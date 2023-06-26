import React from 'react';

import PossibleBomb from './PossibleBomb';

export default class Board extends React.Component {
	constructor(props){
		super(props);
	}

	handleClick(isBomb){
		console.log(isBomb);
	}

	render(){
		return (
			<div>
				{this.props.squares}
			</div>
		)
	}
}