import React, { useState } from 'react';
import Board from './Board';
import Scores from './Scores';

import PossibleBomb from './PossibleBomb';

export default class CampoMinado extends React.Component {
	constructor(props){
		super(props);

		this.MAX_SQUARES = Math.pow(10, 2);
		this.LADO = Math.sqrt(this.MAX_SQUARES);
		let idIterator = this.getGeneratorId(this.LADO);

		/*
		[[bomb, bomb, bomb],
		[bomb, bomb, bomb],
		[bomb, bomb, bomb]]
		 */
		let arrayBombs = Array(this.LADO)
			.fill(Array(this.LADO).fill())
			.map(line => 
				line.map(e => 
					({
						id: idIterator.next().value,
						isBomb: Math.random() >= 0.80,
						wasClicked: false,
						displayValue: 0,
						_equals: function(id){
							// console.log('this', this.id);
							return this.id.x === id.x && this.id.y === id.y;
						}
					})
				)
			);

		/* Mock de bombs */
		// arrayBombs[0][2].isBomb = true;
		// arrayBombs[1][2].isBomb = true;
		// arrayBombs[3][2].isBomb = true;
		// arrayBombs[1][1].isBomb = true;

		arrayBombs.map((line, lineIndex, matriz) => {
				/** ATENÇÃO!!!!
				 * Javascript não possui array bidimensiona.
				 * Para solucionar isso, matriz é um array de arrays,
				 * onde o primeiro array representam as linhas da minha matriz
				 * e o segunto array, as "colunas" desta linha, que vem a ser um 
				 * único objeto.
				 * No plano carteziano (que é para onde minha função geradora de
				 * id foi feita), buscamos primeiro a coluna X e depois a linha Y,
				 * aqui, temos que buscar o contrário, primeiro nossa linha Y e 
				 * depois nossa coluna X.
				 *
				 * Por este motivo, para simular um array bidimensional e lê-lo
				 * com pares ordenados similares aos do plano carteziano, x e y
				 * foram invertidos nos parâmetros desta função.
				 */
				matriz.incrementDisplay = function(y, x){
					if (this[x] && this[x][y]){
						// console.log("Incrementando", this[x][y].id)
						if (!this[x][y].isBomb){
							this[x][y].displayValue++;
						}
					}
				}


				line.map((bomb, i) => {
					// console.log("Bomb =>", bomb);

					if (!bomb.isBomb)
						return bomb;

					// console.log('---- BOMBA ENCONTRADA ----', bomb.id);

					let {x, y} = bomb.id;
					bomb.displayValue = "*";

					/* Laterais */
					matriz.incrementDisplay(x, y+1);
					matriz.incrementDisplay(x, y-1);
					matriz.incrementDisplay(x+1, y);
					matriz.incrementDisplay(x-1, y);

					/* Diagonais */
					matriz.incrementDisplay(x+1, y+1);
					matriz.incrementDisplay(x+1, y-1);
					matriz.incrementDisplay(x-1, y+1);
					matriz.incrementDisplay(x-1, y-1);


					// console.log("Before", matriz[x][y]);
					// matriz.get(x, y).displayValue = 'CARALHO MAANOOO';
					// console.log("After", matriz[x][y]);

				})
			}
		)

		this.state = {
			squares: arrayBombs
		}
	}

	getGeneratorId(cols){
		return (function* idMaker(cols){
			let x = 0;
			let y = 0;

			while (y < cols) {
				yield {x: x++,y}

				if (x >= cols){
					x = 0;
					++y;
				}
			}
		})(cols)
	}

	recoverObjectIdByHtmlId(htmlId){
		let [y, x] = htmlId.split('-');
		return {y, x};
	}

	_callUp({x, y}, cb){
		// console.log("[_callUp] y =>", y);
		( --y >= 0) && cb({x, y});
	}
	_callDown({x, y}, cb){
		// console.log("[_callDown] y =>", y);
		( ++y < this.LADO) && cb({x, y});
	}
	_callLeft({x, y}, cb){
		// console.log("[_callLeft] x =>", x);
		( --x >= 0) && cb({x, y});
	}
	_callRight({x, y}, cb){
		// console.log("[_callRight] x =>", x);
		( ++x < this.LADO) && cb({x, y});
	}

	_callUpRight({x, y}, cb){
		// console.log("[_callUpRight] y =>", y);
		( --y >= 0) && ( ++x < this.LADO) && cb({x, y});
	}
	_callUpLeft({x, y}, cb){
		// console.log("[_callUpLeft] y =>", y);
		( --y >= 0) && ( --x >= 0) && cb({x, y});
	}
	_callDownRight({x, y}, cb){
		// console.log("[_callDownRight] y =>", y);
		( ++y < this.LADO) && ( ++x < this.LADO) && cb({x, y});
	}
	_callDownLeft({x, y}, cb){
		// console.log("[_callDownLeft] y =>", y);
		( ++y < this.LADO) && ( --x >= 0) && cb({x, y});
	}

	callInAllDirections({x, y}, cb){
		this._callUp({x,y}, cb);
		this._callDown({x,y}, cb);
		this._callLeft({x,y}, cb);
		this._callRight({x,y}, cb);

		this._callUpRight({x,y}, cb);
		this._callUpLeft({x,y}, cb);
		this._callDownRight({x,y}, cb);
		this._callDownLeft({x,y}, cb);
	}

	openNeighborsBecouseItsZero({x, y}){
		/* Em todas as direções, todos usarão a mesma referência do state.squares */
		let newSquares = this.state.squares.slice();

		let recourse = ({x, y}) => {
			// console.info("CALLBACK - ZERADO:", x, y)

			this.callInAllDirections({x, y}, ({x, y}) => {
				let bomb = newSquares[x][y];
				// console.log("PossibleBomb vizinha", bomb.id, bomb.isBomb, bomb.displayValue);
				
				if (bomb.wasClicked)
					return;
				if (bomb.isBomb)
					return;

				bomb.wasClicked = true;
				if (bomb.displayValue !== 0)
					return;

				// console.log("Chamando recursiva para ", x, y)
				recourse({x,y});				
			});
		};

		recourse({x, y})
	}

	handleClick(e){
		let squares = this.state.squares.slice();
		let id = this.recoverObjectIdByHtmlId(e.target.id);
		let {x, y} = id;

		console.warn(squares[x][y].id);
		let bomb = squares[x][y];
		bomb.wasClicked = true;

		if (bomb.isBomb)
			console.warn("BOOOOMMMM")
		else if (bomb.displayValue === 0)
			this.openNeighborsBecouseItsZero(id);

		this.setState({squares});

		setTimeout(() => {
			if (bomb.isBomb)
				alert("BOOOOMMMM")
		},100)
		// console.log('atualizado');
	}

	// bomb.wasClicked ? bomb.isBomb : false
	getSquaresBombs(n){
		let linguiçona = [];

		this.state.squares.map(line => {
			line.map(bomb => {
				linguiçona.push(bomb);
				return bomb;
			})
		})

		let linguiçonaHTML = linguiçona.map(bomb => {
			let bombId = bomb.id.x + '-' + bomb.id.y;
			return <PossibleBomb 
								key={bombId}
								id={bombId}
								wasClicked={bomb.wasClicked}
								isBomb={bomb.isBomb} 
								clickBomb={e => this.handleClick(e)}>
									{bomb.wasClicked ? bomb.displayValue : ""}
						</PossibleBomb>
		})
									// {bomb.wasClicked ? bomb.displayValue : ""}

		// console.log('[LINGUIÇONA]', linguiçonaHTML);

		let board = [];

		let rowLength = Math.sqrt(n);
		let start = 0;
		let end = rowLength;
		let times = rowLength;
		while(times-- > 0){
			// console.log("[SLICE]", linguiçonaHTML.slice(start, end));
			board.push(<div key={times} className="board-row">{linguiçonaHTML.slice(start, end)}</div>);
			start += rowLength;
			end += rowLength;
		}
 
		// console.log("[BOARD]", board);

 		return board;
	}


	render(){
		let bombs = this.getSquaresBombs(this.MAX_SQUARES);
		// console.log("Render", bombs)
		// bombs = ['poha', 'caralho', 'buceta'].map(palavrao => <h1>palavrao</h1>)
		// console.log(bombs);
		return (
			<div className="col-md-10 col-sm-12 col-md-offset-1">
				<Scores />
				<Board squares={bombs} />
			</div>
		)
	}
}
