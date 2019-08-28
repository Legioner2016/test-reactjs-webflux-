const React = require('react');
import {FormattedMessage } from "react-intl";

/* Label component (internatialization) */
export class Label extends React.Component{

	constructor(props) {
		super(props);
	}
	
	
	render() {
		return (
				<label className={this.props.className} htmlFor={this.props.htmlFor}>
					<FormattedMessage id={this.props.messageId} defaultMessage={this.props.defaultMessage} />
				</label>
		)
	}
}