
class Control extends React.Component {
    constructor(props) {
        super(props);
        this.state = { };
        this.updateDoTopic = this.updateDoTopic.bind(this);
        this.updateDoText = this.updateDoText.bind(this);
        this.handlePublishMessage = this.handlePublishMessage.bind(this);
        this.handleRequestMessage = this.handleRequestMessage.bind(this);
        this.handleDrainMessages = this.handleDrainMessages.bind(this);

        this.updateCreateTopic = this.updateCreateTopic.bind(this);
        this.handleCreatePublisher = this.handleCreatePublisher.bind(this);
        this.handleCreateSubscriber = this.handleCreateSubscriber.bind(this);

    }

    updateDoTopic(e) {
        this.setState(state => ({
            doTopic: event.target.value
        }));
    }

    updateDoText(e) {
        this.setState(state => ({
            doText: event.target.value
        }));
    }

    updateCreateTopic(e) {
        this.setState(state => ({
            createTopic: event.target.value
        }));
    }

    handlePublishMessage(e) {
        axios.post('/subject/' + this.state.doTopic + '/publish', this.state.doText)
            .then(function (response) {
                // handle success
                console.log(response);
            });
    }

    handleRequestMessage(e) {
        axios.post('/subject/' + this.state.doTopic + '/request', this.state.doText)
            .then(function (response) {
                // handle success
                console.log(response);
            });
    }

    handleDrainMessages(e) {
        axios.get('/subject/' + this.state.doTopic + '/drain')
            .then(function (response) {
                // handle success
                console.log(response);
            });
    }

    handleCreatePublisher(e) {
        this.props.onCreatePublisher(this.state.createTopic);
    }

    handleCreateSubscriber(e) {
        this.props.onCreateSubscriber(this.state.createTopic);
    }

    render() {
        return React.createElement('header', null,
            React.createElement('div', null,
                React.createElement('span', {'className': 'type'}, "DO"),
                React.createElement('input', {'type': 'text', onChange: this.updateDoTopic}),
                React.createElement('input', {'type': 'text', onChange: this.updateDoText}),
                React.createElement('button', {onClick: this.handlePublishMessage}, 'Publish'),
                React.createElement('button', {onClick: this.handleRequestMessage}, 'Request'),
                React.createElement('button', {onClick: this.handleDrainMessages}, 'Drain')),
            React.createElement('div', null,
                React.createElement('span', {'className': 'type'}, "CREATE"),
                React.createElement('input', {'type': 'text', onChange: this.updateCreateTopic}),
                React.createElement('button', {onClick: this.handleCreatePublisher}, 'Publisher'),
                React.createElement('button', {onClick: this.handleCreateSubscriber}, 'Subscriber')));
    }
}

class Subscriber extends React.Component {
    constructor(props) {
        super(props);
        this.state = { enabled: true, messages: [], delay: 0 };
        this.handleToggleEnable = this.handleToggleEnable.bind(this);
    }

    componentDidMount() {
        if (this.state.enabled) {
            this.start();
        }
    }

    componentWillUnmount() {
        if (this.state.enabled) {
            this.stop();
        }
    }

    handleToggleEnable(e) {
        if (this.state.enabled) {
            this.setState(state => ({
                enabled: false,
            }));
            this.stop();
        } else {
            this.setState(state => ({
                enabled: true
            }));
            this.start();
        }
    }

    start() {
        if (!this.state.active) {
            const _self = this;
            const conn = new WebSocket('ws://localhost:6001/stream/subject/' + this.props.topic);

            conn.onclose = function (event) {
                console.log('Connection closed')
            };

            conn.onmessage = function (event) {
                console.log('Message received.');
                var data = JSON.parse(event.data);
                _self.setState(state => ({
                    messages: state.messages.concat(data)
                }));
            };

            this.setState(state => ({
                active: true,
                conn: conn
            }));
        }
    }

    stop() {
        if (this.state.active) {
            this.state.conn.close();
            this.setState(state => ({
                active: false,
                conn: null
            }));
        }
    }

    render() {
        return React.createElement('section', {'className': 'card subscriber' + (this.state.enabled ? ' enabled' : ' disabled')},
            React.createElement('div', {'className': 'card-header'},
                React.createElement('span', {'className': 'type'}, 'SUB'),
                React.createElement('span', {'className': 'topic'}, this.props.topic),
                React.createElement('button', {onClick: this.handleToggleEnable}, 'Toggle')),
            React.createElement('div', {'className': 'card-section'},
                React.createElement('div', {'className': 'code'}, 'msg -> yo')),
            React.createElement('div', {'className': 'card-section'},
                this.state.messages.map(msg =>
                React.createElement('div', {'className': 'message', 'key': msg.id},
                    React.createElement('div', {'className': 'message-timestamp'}, msg.timestamp),
                    React.createElement('div', {'className': 'message-direction'}, msg.direction),
                    React.createElement('div', {'className': 'message-content'}, msg.content),
                    React.createElement('div', {'className': 'message-reply-to'}, msg.replyTo)))));
    }
}

class Publisher extends React.Component {
    constructor(props) {
        super(props);
        this.state = { text: '', messages: [] };
        this.updateText = this.updateText.bind(this);
        this.handlePublishMessage = this.handlePublishMessage.bind(this);
        this.handleRequestMessage = this.handleRequestMessage.bind(this);
        this.handleToggleRepeat= this.handleToggleRepeat.bind(this);
    }

    updateText(e) {
        this.setState(state => ({
            text: event.target.value
        }));
    }

    handlePublishMessage(e) {
        var _self = this;
        _self.setState(state => ({
            messages: state.messages.concat({timestamp: '3232', direction: '<', content: state.text})
        }));
        axios.post('/subject/' + this.props.topic + '/publish', _self.state.text)
            .then(function (response) {
                // handle success
                console.log(response);
            });
    }

    handleRequestMessage(e) {
        var _self = this;
        _self.setState(state => ({
            messages: state.messages.concat({timestamp: '06:03:42', direction: '<', content: state.text})
        }));
        axios.post('/subject/' + this.props.topic + '/request', _self.state.text)
            .then(function (response) {
                // handle success
                console.log(response);
                _self.setState(state => ({
                    messages: state.messages.concat(response.data)
                }));
            });
    }

    handleToggleRepeat(e) {

    }

    render() {
        return React.createElement('section', {'className': 'card publisher'},
            React.createElement('div', {'className': 'card-header'},
                React.createElement('span', {'className': 'type'}, 'PUB'),
                React.createElement('span', {'className': 'topic'}, this.props.topic)),
            React.createElement('div', {'className': 'card-section'},
                React.createElement('div', {'className': 'service-config'},
                    React.createElement('div', null,
                        React.createElement('span', {'className': 'type'}, 'DO'),
                        React.createElement('input', {'type': 'text', onChange: this.updateText}),
                        React.createElement('button', {onClick: this.handlePublishMessage}, 'Publish'),
                        React.createElement('button', {onClick: this.handleRequestMessage}, 'Request')),
                    React.createElement('div', null,
                        React.createElement('span', {'className': 'type'}, 'REPEAT'),
                        React.createElement('input', {'type': 'text', onChange: this.updateText}),
                        React.createElement('button', {onClick: this.handleToggleRepeat}, 'Publish')))),
            React.createElement('div', {'className': 'card-section'},
                this.state.messages.map(msg =>
                React.createElement('div', {'className': 'message', 'key': msg.id},
                    React.createElement('div', {'className': 'message-timestamp'}, msg.timestamp),
                    React.createElement('div', {'className': 'message-direction'}, msg.direction),
                    React.createElement('div', {'className': 'message-content'}, msg.content),
                    React.createElement('div', {'className': 'message-reply-to'}, msg.replyTo)))));
    }
}

class Topics extends React.Component {
    render() {
        return React.createElement('main', null,
            this.props.publishers.map(pub =>
                React.createElement(Publisher, {key: pub.id, topic: pub.topic})),
            this.props.subscribers.map(sub =>
                React.createElement(Subscriber, {key: sub.id, topic: sub.topic}))
        );
    }
}


class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = { nextId: 1, publishers: [], subscribers: [] };
        this.handleCreatePublisher = this.handleCreatePublisher.bind(this);
        this.handleCreateSubscriber = this.handleCreateSubscriber.bind(this);
        this.handleRemovePublisher = this.handleRemovePublisher.bind(this);
        this.handleRemoveSubscriber= this.handleRemoveSubscriber.bind(this);
    }
    handleCreatePublisher(topic) {
        this.setState(state => ({
            nextId: state.nextId + 1,
            publishers: state.publishers.concat({id: state.nextId, topic: topic})
        }));
    }
    handleCreateSubscriber(topic) {
        this.setState(state => ({
            nextId: state.nextId + 1,
            subscribers: state.subscribers.concat({id: state.nextId, topic: topic})
        }));
    }
    handleRemovePublisher(id) {

    }
    handleRemoveSubscriber(id) {

    }
    render() {
        return React.createElement('div', null,
            React.createElement(Control, {onCreatePublisher: this.handleCreatePublisher, onCreateSubscriber: this.handleCreateSubscriber}),
            React.createElement(Topics, {publishers: this.state.publishers, subscribers: this.state.subscribers})
        );
    }
}

ReactDOM.render(React.createElement(App, null), document.getElementById('tech1'));