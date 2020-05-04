import React from 'react';
import { makeStyles } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Chip from '@material-ui/core/Chip';
import AppService from '../services/AppService';

const app = new AppService();

const useStyles = makeStyles(theme => ({
  root: {
    margin: theme.spacing(1),
    padding: theme.spacing(5),
    minHeight: "90vh"
  },
  divider: {
    margin: theme.spacing(3,0,5,0)
  },
  success: {
    color: "white",
    backgroundColor: theme.palette.success.main
  },
  warning: {
    color: "white",
    backgroundColor: theme.palette.warning.main
  },
  error: {
    color: "white",
    backgroundColor: theme.palette.error.main
  }
}));

export default function SystemSettings({messageQueue}){
  const classes = useStyles();

  const [graphOpts, setGraphOpts] = React.useState({
    host: "",
    port: 7687
  })
  const handleSetGraphHost = (e) => {
    setGraphOpts({...graphOpts, host: e.target.value})
  }
  const handleSetGraphPort = (e) => {
    const val = e.target.value;
    if(val){
      setGraphOpts({...graphOpts, port: parseInt(val)})
    }
  }
  const handleSaveGraphSettings = async () => {
    let settings = await app.mergeSettings({graph: graphOpts});
    await app.loadSettings(settings);
    messageQueue.push({
      severity: "success",
      text: "Successfully saved System Settings"
    })
  }
  React.useEffect(() => {
    let active = true;
    app.getSettings().then(settings => {
      if (active) {
        setGraphOpts(settings.graph);
      }
    });
    return () => { active = false; };
  }, []);

  const [status, setStatus] = React.useState({
    checked: false,
    graphReady: false,
    dialogflowConnected: false
  })
  React.useEffect(() => {
    let active = true;
    app.getStatus().then(status => {
      if (active) {
        setStatus({...status, checked: true});
      }
    });
    return () => { active = false; };
  }, []);

  return (
    <Paper className={classes.root}>
      <Typography variant="h6">System Status and Settings</Typography>
      <Divider className={classes.divider}/>
      <Grid container spacing={1}>
        <Grid item xs={12}> 
          <Typography variant="subtitle1">
            Graph Data Service: &nbsp;&nbsp;&nbsp;
            {status.checked? (status.graphReady? (
              <Chip className={classes.success} label="Ready"/>
            ):(
              <Chip className={classes.warning} label="Loading"/>
            )):(
              <Chip label="Checking"/>
            )}
          </Typography>
        </Grid>
        <Grid item xs={6} md={4} lg={3}>
          <TextField label="Graph Data Host"
            fullWidth variant="filled"
            helperText="Edit if hostname is different from current page"
            value={graphOpts.host} onChange={handleSetGraphHost}/>
        </Grid>
        <Grid item xs={6} md={4} lg={3}>
          <TextField label="Graph Data Port"
            fullWidth variant="filled"
            helperText="Default port is 7687, change if mapped differently by Docker"
            type="number" value={graphOpts.port} onChange={handleSetGraphPort}/>
        </Grid>
        <Grid item xs={12} md={4} lg={6}/>
        <Grid item xs={12}>
          <Button variant="contained" color="primary" onClick={handleSaveGraphSettings}>Save Settings</Button>
        </Grid>
        <Grid item xs={12}>
          <Divider className={classes.divider}/>
          <Typography variant="subtitle1">
            Dialogflow Service: &nbsp;&nbsp;&nbsp;
            {status.checked? (status.dialogflowConnected? (
              <Chip className={classes.success} label="Connected"/>
            ):(
              <Chip className={classes.error} label="Disconnected"/>
            )):(
              <Chip label="Checking"/>
            )}
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <Typography variant="subtitle2">
            * Free text search is not supported if Dialogflow service disconnected, e.g. backend internet connection lost.
          </Typography>
        </Grid>
      </Grid>
    </Paper>
  );
}