import React from 'react';
import { makeStyles } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Slider from '@material-ui/core/Slider';
import DemoService from '../services/DemoService';

const demo = new DemoService();

const useStyles = makeStyles(theme => ({
  root: {
    margin: theme.spacing(1),
    padding: theme.spacing(5),
    minHeight: "90vh"
  },
  divider: {
    margin: theme.spacing(3,0,5,0)
  },
  table: {
    padding: theme.spacing(0,0,2,0)
  },
  slider: {
    maxWidth: 120
  }
}));

export default function ResourceSimulation({messageQueue}){
  const classes = useStyles();

  const [load, setLoad] = React.useState(5);
  const [simulation, setSimulation] = React.useState(0);
  const handleLoadChange = e => {
    setLoad(parseFloat(e.target.value))
  }
  React.useEffect(() => {
    let active = true;
    demo.getCurrentLoad("Request_to_Answer").then(({load}) => {
      if (active && load > 0) {
        setLoad(load.toFixed(1));
      }
    });
    return () => { active = false; };
  }, []);

  const simulateLoad = async () => {
    let resp = await demo.simulateLoad({
      relationship: "Request_to_Answer",
      load,
	    startNode: "{name:'Customer1'}",
	    endNode: "{name:'CIR1'}"
    });
    if(resp.success && messageQueue.length < 1){
      messageQueue.push({
        severity: "success",
        text: "Simulation of Load Distribution completed within Request-to-Answer process"
      })
    }
    setSimulation(simulation + 1);
  }

  const [resources, setResources] = React.useState([]);
  React.useEffect(() => {
    let active = true;
    demo.getResourceLoad("Request_to_Answer").then(records => {
      if (active) {
        setResources(records);
      }
    });
    return () => { active = false; };
  }, [simulation]);

  const saveResourceConfiguration = async (records) => {
    let resp = await demo.postResourceLoad(records);
    if(resp.success && messageQueue.length < 1){
      messageQueue.push({
        severity: "success",
        text: "Successfully saved Resource Configuration"
      })
    }
    simulateLoad();
  }
  const handleResourceChange = id => ((e, value) => {
    const records = resources.map(r => r.id === id? {...r, resource: value}: {...r});
    saveResourceConfiguration(records);
  })
  const handleLoadWeightChange = id => ((e, value) => {
    const records = resources.map(r => r.id === id? {...r, loadWeight: value}: {...r});
    saveResourceConfiguration(records);
  })
  
  return (
    <Paper className={classes.root}>
      <Typography variant="h6">Simulation Resource and Load in Request-to-Answer</Typography>
      <Divider className={classes.divider}/>
      <Grid container spacing={1}>
        <Grid item xs={12}> 
          <Typography variant="subtitle1">Load Simulation</Typography>
        </Grid>
        <Grid item xs={12}>
          <Typography variant="subtitle2">
            * Load will be distributed from upstream to downstream based on a ratio (weight) defined by each node's load distribution property.
          </Typography>
        </Grid>
        <Grid item xs={8} md={6} lg={4}>
          <TextField label="Total Load"
            fullWidth variant="filled"
            helperText="Customer enquiry rate at starting point of process stream"
            type="number" value={load} onChange={handleLoadChange}/>
        </Grid>
        <Grid item xs={4} md={6} lg={8}/>
        <Grid item xs={12}>
          <Button variant="contained" color="primary" onClick={simulateLoad}>Simulate Load</Button>
        </Grid>
        <Grid item xs={12}>
          <Divider className={classes.divider}/>
          <Typography variant="subtitle1">Resource Configuration</Typography>
        </Grid>
        <Grid item xs={12}>
          <TableContainer className={classes.table}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell align="left">Node ID</TableCell>
                  <TableCell align="left">Node Type</TableCell>
                  <TableCell align="left">Name</TableCell>
                  <TableCell align="right">Resource Count</TableCell>
                  <TableCell align="right">Load Distribution</TableCell>
                  <TableCell align="right">Simulated Load</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {resources.map((row) => (
                  <TableRow key={`${row.id}-${row.resource.toFixed(0)}-${row.loadWeight.toFixed(0)}`}>
                    <TableCell align="left">{row.id}</TableCell>
                    <TableCell align="left">{row.label}</TableCell>
                    <TableCell align="left">{row.name}</TableCell>
                    <TableCell align="right">
                      <Slider className={classes.slider}
                        defaultValue={row.resource} valueLabelDisplay="auto" 
                        step={1} min={1} max={10} 
                        onChangeCommitted={handleResourceChange(row.id)}/>
                    </TableCell>
                    <TableCell align="right">
                      <Slider className={classes.slider} 
                        defaultValue={row.loadWeight} valueLabelDisplay="auto" 
                        step={1} min={1} max={100}
                        onChangeCommitted={handleLoadWeightChange(row.id)}/>
                    </TableCell>
                    <TableCell align="right">{row.load.toFixed(2)}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
      </Grid>
    </Paper>
  );
}