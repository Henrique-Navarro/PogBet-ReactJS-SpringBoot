import React from 'react';
import './PossibleBomb.css'
export default class PossibleBomb extends React.Component {
	constructor(props){
		super(props);

		this.handleClick = this.handleClick.bind(this);
	}

	handleClick(e){
		this.props.clickBomb(e);
	}

	render(){
		let style = {
			backgroundColor: this.props.wasClicked ? (this.props.isBomb ? "purple" : "#009871")  : "darkgoldenrod"
            
		}

		return (
			<button className="square"
            
							id={this.props.id}
							onClick={this.handleClick}
							data-bomb={this.props.isBomb}
							style={style} >
							{this.props.children}
			</button>
		)
	}
}