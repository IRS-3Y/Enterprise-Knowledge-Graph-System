import React from 'react';
import { makeStyles } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import DemoService from '../services/DemoService';

const demo = new DemoService();

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

export default function ResourceSimulation({messageQueue}){
  const classes = useStyles();

  const [load, setLoad] = React.useState(100);
  const handleLoadChange = e => {
    setLoad(parseFloat(e.target.value))
  }
  const handleSimulateLoad = async () => {
    let resp = await demo.simulateLoad({
      relationship: "Request_to_Answer",
      load,
	    startNode: "{name:'Customer1'}",
	    endNode: "{name:'CIR1'}"
    });
    if(resp.success){
      messageQueue.push({
        severity: "success",
        text: "Simulation of Load Distribution completed within Request-to-Answer process"
      })
    }
  }
  return (
    <Paper className={classes.root}>
      <Typography variant="h6">Simulation Resource and Load in Request-to-Answer</Typography>
      <Divider className={classes.divider}/>
      <Grid container spacing={1}>
        <Grid item xs={12}> 
          <Typography variant="subtitle1">Load Distribution</Typography>
        </Grid>
        <Grid item xs={6} md={4} lg={3}>
          <TextField label="Total Load"
            fullWidth variant="filled"
            type="number" value={load} onChange={handleLoadChange}/>
        </Grid>
        <Grid item xs={6} md={8} lg={9}/>
        <Grid item xs={12}>
          <Button variant="contained" color="primary" onClick={handleSimulateLoad}>Simulate Load</Button>
        </Grid>
      </Grid>
    </Paper>
  );
}