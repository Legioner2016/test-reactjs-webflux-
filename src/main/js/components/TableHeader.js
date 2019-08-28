const React = require('react');
import {FormattedMessage } from "react-intl";

/* Label analog for table header row */
export class TableHeader extends React.Component{

	constructor(props) {
		super(props);
	}
	
	render() {
		if (this.props.messageId == null) {
			return (
		    	<th className={this.props.className}>{this.props.text}</th>
			)
		}
		return (
	    		<th className={this.props.className}>
    				<FormattedMessage id={this.props.messageId} defaultMessage={this.props.defaultMessage} />
    			</th>
		)
	}
}