import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import SearchBar from './components/search/SearchBar';
import GraphView from './views/GraphView';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(1),
  }
}));

export default function App() {
  const classes = useStyles();

  const [cypher, setCypher] = React.useState("");

  const handleSearchResult = (result) => {
    if(result){
      setCypher(`match(n:Technology{longName:'${result.name}'})-[r]->(m) return *`);
    }
  }
  return (
    <div className={classes.root}>
      <AppBar position="fixed">
        <Toolbar>
          <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
            <MenuIcon />
          </IconButton>
          <Typography variant="h6">
            EKGS
          </Typography>
          <SearchBar onSearchResult={handleSearchResult}/>
        </Toolbar>
      </AppBar>
      <GraphView cypher={cypher}/>
    </div>
  );
}
