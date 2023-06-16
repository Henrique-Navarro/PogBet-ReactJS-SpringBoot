import React, { useState } from 'react';
import './CampoMinado.css';

const generateEmptyBoard = (rows, columns) => {
  const board = [];
  for (let i = 0; i < rows; i++) {
    board[i] = [];
    for (let j = 0; j < columns; j++) {
      board[i][j] = { value: 0, isRevealed: false };
    }
  }
  return board;
};

const placeBombs = (board, numBombs) => {
  const rows = board.length;
  const columns = board[0].length;
  let bombsPlaced = 0;

  while (bombsPlaced < numBombs) {
    const row = Math.floor(Math.random() * rows);
    const column = Math.floor(Math.random() * columns);

    if (board[row][column].value !== -1) {
      board[row][column].value = -1;
      bombsPlaced++;
    }
  }
};

const countAdjacentBombs = (board, row, column) => {
  const rows = board.length;
  const columns = board[0].length;
  let count = 0;

  for (let i = -1; i <= 1; i++) {
    for (let j = -1; j <= 1; j++) {
      const newRow = row + i;
      const newColumn = column + j;

      if (
        newRow >= 0 &&
        newRow < rows &&
        newColumn >= 0 &&
        newColumn < columns &&
        board[newRow][newColumn].value === -1
      ) {
        count++;
      }
    }
  }

  return count;
};

const revealEmptyCells = (board, row, column) => {
  const rows = board.length;
  const columns = board[0].length;

  const revealCell = (r, c) => {
    if (
      r >= 0 &&
      r < rows &&
      c >= 0 &&
      c < columns &&
      !board[r][c].isRevealed
    ) {
      board[r][c].isRevealed = true;

      if (board[r][c].value === 0) {
        for (let i = -1; i <= 1; i++) {
          for (let j = -1; j <= 1; j++) {
            revealCell(r + i, c + j);
          }
        }
      }
    }
  };

  revealCell(row, column);
};

const Cell = ({ cell, onClick }) => {
  const handleClick = () => {
    onClick(cell);
  };

  return (
    <div
      className={`cell ${cell.isRevealed ? 'revealed' : ''}`}
      onClick={handleClick}
    >
      {cell.isRevealed ? (cell.value === -1 ? '💣' : cell.value) : ''}
    </div>
  );
};

const CampoMinado = () => {
  const rows = 10;
  const columns = 10;
  const numBombs = 10;

  const [board, setBoard] = useState(generateEmptyBoard(rows, columns));

  const handleCellClick = (row, column) => {
    if (board[row][column].isRevealed) return;

    if (board[row][column].value === -1) {
      // Game over logic
      alert('Game Over');
      setBoard(generateEmptyBoard(rows, columns));
      return;
    }

    board[row][column].isRevealed = true;

    if (board[row][column].value === 0) {
      revealEmptyCells(board, row, column);
    }

    setBoard([...board]);
  };

  if (board.flat().filter(cell => cell.isRevealed).length === rows * columns - numBombs) {
    // Game won logic
    alert('Congratulations! You won the game!');
    setBoard(generateEmptyBoard(rows, columns));
  }

  placeBombs(board, numBombs);

  return (
    <div className="board">
      {board.map((row, rowIndex) => (
        <div key={rowIndex} className="row">
          {row.map((cell, columnIndex) => (
            <Cell
              key={columnIndex}
              cell={cell}
              onClick={() => handleCellClick(rowIndex, columnIndex)}
            />
          ))}
        </div>
      ))}
    </div>
  );
};

export default CampoMinado;
