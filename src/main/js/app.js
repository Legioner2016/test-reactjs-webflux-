const React = require('react');
const ReactDOM = require('react-dom');
import { Route, NavLink, HashRouter} from "react-router-dom";
import {UserList} from './components/UserList';
import {NewUser} from './components/NewUser';
import {NavigationMenuItem} from './components/NavigationMenuItem';
import {LongRequest} from './components/LongRequest';
import {IntlProvider, addLocaleData, FormattedMessage } from "react-intl";
import locale_en from 'react-intl/locale-data/en';
import locale_ru from 'react-intl/locale-data/ru';
import messages from './translation/messages';
import { Redirect } from 'react-router';

/* Main React application file  */

addLocaleData([...locale_en, ...locale_ru]);

window.current_language = navigator.language.split(/[-_]/)[0];

class App extends React.Component {

	constructor(props) {
		super(props);
	    this.state = {
    		language : navigator.language.split(/[-_]/)[0]	
	    };
	}
	
	onChange(e) {
		e.preventDefault();
  	  	this.setState({
  		  	language : window.current_language,
  		  	toList : true
  	  	});
	}
	
	chnageLang(lang, e) {
		  e.preventDefault();
		  window.current_language = lang; 
    	  this.setState({
    		  language : lang 
    	  });
	}

	render() {
		return (
		<IntlProvider locale={this.state.language} messages={messages[this.state.language]}>
			<HashRouter>
				<div className="col-12">
					<nav className="navbar navbar-expand-md navbar-light bg-light border rounded">

					<span className="navbar-brand">Test ReactJS application</span>

					<button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbar">
						<span className="navbar-toggler-icon"></span>
					</button>

						<div className="navbar-collapse collapse" id="collapsingNavbar">
        
							<ul className="navbar-nav">
							        <li className="nav-item">
										<NavigationMenuItem link="/userlist" messageId="menu.userlist" defaultMessage="Users list" />
									</li>
								    <li className="nav-item">
										<NavigationMenuItem link="/newuser" messageId="menu.adduser" defaultMessage="Add user" />
									</li>
								    <li className="nav-item">
										<NavigationMenuItem link="/longrequest" messageId="menu.longrequest" defaultMessage="Long request test" />
									</li>
							</ul>

							<ul className="navbar-nav ml-auto">
								<li className="nav-item mr-sm-2">
								</li>
								{ this.state.language == 'en'
				            		? <li className="nav-item" ><button onClick={(e) => this.chnageLang("ru", e)}>ru</button></li>
				              		: <li className="nav-item" ><button onClick={(e) => this.chnageLang("en", e)}>en</button></li>
				            	}
							</ul>
        
						</div>
						
						<input type="text" id="data_react_interact" style={{width: '1px', height: '1px'}} onChange={(e) => this.onChange(e)} />
					</nav>
					<div className="content">
						<Route path="/userlist" component={UserList}/>
						<Route path="/newuser" component={NewUser}/>
						<Route path="/longrequest" component={LongRequest}/>
						{ this.state.toList
		            		? <Redirect to='/userlist' />
		              		: null
		            	}
					</div>
				</div>
			</HashRouter>
			</IntlProvider>	
        )

		
	}
}

ReactDOM.render(
		<App />,
		document.getElementById('react')
)