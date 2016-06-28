import {createElement, Element} from 'react';
import {FormattedMessage} from 'react-intl';
import {connect} from 'react-redux';
import map from 'lodash/fp/map';
import sortBy from 'lodash/fp/sortBy';
import styles from './match-group.css';

// import styles from './ladder.css';
import Heading from '../heading/heading';

const orderPlayers = map((player) => (
  <div key={player.email}>
    {player.firstName} {player.lastName}
  </div>
));

const orderTeams = map((team) => (
  <div className={styles.table}>
    <div className={styles.entry}>
      <FormattedMessage
        id='rank'
        defaultMessage='Rank: '
      />
      {team.rank}
    </div>
    <div className={styles.entry}>
      <FormattedMessage
        id='player1'
        defaultMessage='First Player: '
      />
      {team.firstPlayer}
    </div>
    <div className={styles.entry}>
      <FormattedMessage
        id='player2'
        defaultMessage='Second Player: '
      />
      {team.secondPlayer}
    </div>
  </div>
));

const MatchGroup = ({
  players,
  teams,
}) : Element => (
  <div className={styles.ladder}>
    <div>
      <Heading kind='huge'>
        <FormattedMessage
          id='playerList'
          defaultMessage='Players'
        />
      </Heading>
    {orderPlayers(players)}
    </div>
    <div>
      <Heading kind='huge'>
        <FormattedMessage
          id='teamList'
          defaultMessage='Teams'
        />
      </Heading>
      {orderTeams(teams)}
    </div>
  </div>
);

export default connect(
  (state) => ({
    players: sortBy('firstName', state.app.players),
    teams: sortBy('rank', state.app.teams),
  }),
  {}
)(MatchGroup);
