import React from 'react';
import { makeStyles } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import AppService from '../services/AppService';

const app = new AppService();

const useStyles = makeStyles(theme => ({
  root: {
    margin: theme.spacing(1),
    padding: theme.spacing(5),
    minHeight: "90vh"
  },
  divider: {
    margin: theme.spacing(1,0,3,0)
  }
}));

export default function SystemSettings(){
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

  return (
    <Paper className={classes.root}>
      <Typography variant="h6">System Status and Settings</Typography>
      <Divider className={classes.divider}/>
      <Grid container spacing={1}>
        <Grid item xs={12}> 
          <Typography variant="subtitle1">Graph Data Service:</Typography>
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
          <Typography variant="subtitle1">Dialogflow Service:</Typography>
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