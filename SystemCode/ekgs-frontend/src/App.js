import React from 'react';
import clsx from 'clsx';
import { makeStyles, useTheme, createMuiTheme } from '@material-ui/core/styles';
import { ThemeProvider } from "@material-ui/styles";
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Drawer from '@material-ui/core/Drawer';
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import SearchBar from './components/search/SearchBar';
import GraphView from './views/GraphView';
import config from './config';

const drawerWidth = 240;

const darkTheme = createMuiTheme({
  palette: {
    type: "dark"
  }
});
const defaultTheme = createMuiTheme();

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  toolbar: {
    paddingTop: theme.spacing(0.5),
    paddingBottom: theme.spacing(0.5)
  },
  menuButton: {
    marginRight: theme.spacing(1),
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
    backgroundColor: theme.palette.primary.dark,
  },
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(1, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(1),
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: -drawerWidth,
  },
  contentShift: {
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: 0,
  },
  white: {
    color: 'white'
  },
  graphSettings: {
    color: 'white',
    padding: theme.spacing(2,1)
  }
}));

export default function App() {
  const classes = useStyles();
  const theme = useTheme();

  const [open, setOpen] = React.useState(false);
  const handleDrawerOpen = () => {
    setOpen(true);
  };
  const handleDrawerClose = () => {
    setOpen(false);
  };

  const [cypher, setCypher] = React.useState("");
  const handleSearchResult = (result) => {
    if(result && result[0]){
      const {name: action, params: {graph}} = result[0];
      if(action === 'view'){
        setCypher(graph);
      }
    }
  }
  const [graphConfig, setGraphConfig] = React.useState(config.graph);
  const handleNodeLabelChange = (e) => {
    setGraphConfig({
      ...graphConfig,
      nodeCaption: e.target.value
    })
  }
  const handleRelationshipLabelChange = (e) => {
    setGraphConfig({
      ...graphConfig,
      relationshipCaption: e.target.value
    })
  }

  const logo = (
    <Typography className={classes.white} variant="h6" noWrap>EKGS</Typography>
  )
  const toolbarMenu = (
    <ButtonGroup variant="text">
      <Button>
        <Typography className={classes.white} variant="subtitle1" noWrap>File</Typography>
      </Button>
      <Button>
        <Typography className={classes.white} variant="subtitle1" noWrap>Help</Typography>
      </Button>
    </ButtonGroup>
  )
  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      <div className={classes.root}>
        <AppBar className={clsx(classes.appBar, {[classes.appBarShift]: open})}
          position="fixed">
          <Toolbar className={classes.toolbar}>
            <IconButton className={clsx(classes.menuButton, open && classes.hide)}
              aria-label="menu"
              edge="start"
              color="inherit"
              onClick={handleDrawerOpen}>
              <MenuIcon />
            </IconButton>
            {open? null: logo}
            {open? toolbarMenu: null}
            <SearchBar onSearchResult={handleSearchResult}/>
          </Toolbar>
        </AppBar>
        <Drawer className={classes.drawer}
          variant="persistent"
          anchor="left"
          open={open}
          classes={{
            paper: classes.drawerPaper,
          }}>
          <div className={classes.drawerHeader}>
            {open? logo: null}
            <IconButton className={classes.white} onClick={handleDrawerClose}>
              {theme.direction === 'ltr' ? <ChevronLeftIcon/> : <ChevronRightIcon/>}
            </IconButton>
          </div>
          <Divider />
          <ThemeProvider theme={darkTheme}>
            <div className={classes.graphSettings}>
              <Typography variant="subtitle1" noWrap>Graph Settings</Typography>
              <TextField label="Node Label" select variant="filled" margin="dense" fullWidth value={graphConfig.nodeCaption} onChange={handleNodeLabelChange}>
                <MenuItem value="name">name</MenuItem>
                <MenuItem value="longName">longName</MenuItem>
                <MenuItem value="shortDescription">shortDescription</MenuItem>
              </TextField>
              <TextField label="Relationship Label" select variant="filled" margin="dense" fullWidth value={graphConfig.relationshipCaption} onChange={handleRelationshipLabelChange}>
                <MenuItem value={true}>shown</MenuItem>
                <MenuItem value={false}>hidden</MenuItem>
              </TextField>
            </div>
          </ThemeProvider>
        </Drawer>
        <main className={clsx(classes.content, {[classes.contentShift]: open})}>
          <div className={classes.drawerHeader} />
          <GraphView config={graphConfig} cypher={cypher}/>
        </main>
      </div>
    </ThemeProvider>
  );
}
