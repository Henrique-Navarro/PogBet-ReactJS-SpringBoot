import React, { useState } from 'react';
import Board from './Board';
import Scores from './Scores';
import PossibleBomb from './PossibleBomb';
import './CampoMinado.css'
import Cav from "../../img/LogoCaveira.svg";

export default class CampoMinado extends React.Component {
	constructor(props){
		super(props);

		this.MAX_SQUARES = Math.pow(10, 2);
		this.LADO = Math.sqrt(this.MAX_SQUARES);
		let idIterator = this.getGeneratorId(this.LADO);
    

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
							return this.id.x === id.x && this.id.y === id.y;
						}
					})
				)
			);


		arrayBombs.map((line, lineIndex, matriz) => {
				matriz.incrementDisplay = function(y, x){
					if (this[x] && this[x][y]){
						if (!this[x][y].isBomb){
							this[x][y].displayValue++;
						}
					}
				}



				line.map((bomb, i) => {

					if (!bomb.isBomb)
						return bomb;
					let {x, y} = bomb.id;
					bomb.displayValue =  ":(";

          /* <img
          className="logoCav"
          src={Cav}
          alt="imagem do logo caveira"
          title="Logo caveira"
        />*/

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

				})
			}
		)

		this.state = {
      squares: arrayBombs,
      aposta: 0
    };
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
		( --y >= 0) && cb({x, y});
	}
	_callDown({x, y}, cb){
		( ++y < this.LADO) && cb({x, y});
	}
	_callLeft({x, y}, cb){
		( --x >= 0) && cb({x, y});
	}
	_callRight({x, y}, cb){
		( ++x < this.LADO) && cb({x, y});
	}

	_callUpRight({x, y}, cb){
		( --y >= 0) && ( ++x < this.LADO) && cb({x, y});
	}
	_callUpLeft({x, y}, cb){
		( --y >= 0) && ( --x >= 0) && cb({x, y});
	}
	_callDownRight({x, y}, cb){
		( ++y < this.LADO) && ( ++x < this.LADO) && cb({x, y});
	}
	_callDownLeft({x, y}, cb){
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
		let newSquares = this.state.squares.slice();

		let recourse = ({x, y}) => {
			this.callInAllDirections({x, y}, ({x, y}) => {
				let bomb = newSquares[x][y];
				
				if (bomb.wasClicked)
					return;
				if (bomb.isBomb)
					return;

				bomb.wasClicked = true;
				if (bomb.displayValue !== 0)
					return;


				recourse({x,y});				
			});
		};

		recourse({x, y})
	}

  handleClick(e) {
    let squares = this.state.squares.slice();
    let id = this.recoverObjectIdByHtmlId(e.target.id);
    let { x, y } = id;
  
    console.warn(squares[x][y].id);
    let bomb = squares[x][y];
    bomb.wasClicked = true;
  
    if (bomb.isBomb) {
      console.warn("BOOOOMMMM");
      this.setState({ aposta: 0 }); // Zera a aposta se houver uma bomba
    } else if (bomb.displayValue === 0) {
      this.openNeighborsBecouseItsZero(id);
    }
  
    let quadradosCertos = squares.flat().filter((bomb) => bomb.wasClicked && !bomb.isBomb).length;
    let resultado = quadradosCertos * 0.5 * this.state.aposta;
    console.log("Resultado:", resultado);
  
    this.setState({ squares });
  
    setTimeout(() => {
      if (bomb.isBomb) {
        alert("BOOOOMMMM");
      }
    }, 100);
  }
  
	getSquaresBombs(n){
		let aux = [];

		this.state.squares.map(line => {
			line.map(bomb => {
				aux.push(bomb);
				return bomb;
			})
		})

		let auxHTML = aux.map(bomb => {
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
							

		let board = [];

		let rowLength = Math.sqrt(n);
		let start = 0;
		let end = rowLength;
		let times = rowLength;
		while(times-- > 0){
			board.push(<div key={times} className="board-row">{auxHTML.slice(start, end)}</div>);
			start += rowLength;
			end += rowLength;
		}
 

 		return board;
	}


  render() {
    let bombs = this.getSquaresBombs(this.MAX_SQUARES);
    return (
      <div className="col-md-10 col-sm-12 col-md-offset-1">
        <Scores />
        <div>
          <label htmlFor="aposta">Valor da Aposta:</label>
          <input
            type="number"
            id="aposta"
            value={this.state.aposta}
            onChange={(e) => this.setState({ aposta: e.target.value })}
          />
        </div>
        <Board squares={bombs} />
      </div>
    );
  }
  
  
}
