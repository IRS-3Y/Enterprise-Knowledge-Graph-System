import React from 'react';
import { makeStyles } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import ErrorBoundary from '../components/core/ErrorBoundary';
import NeoVisGraph from '../components/visualization/NeoVisGraph';

const useStyles = makeStyles(theme => ({
  root: {
    margin: theme.spacing(1),
    height: "100vh"
  }
}));

export default function GraphView({cypher}) {
  const classes = useStyles();
  return (
    <Card className={classes.root}>
      <Grid>
        <Grid item>
          <ErrorBoundary>
            <NeoVisGraph cypher={cypher}/>
          </ErrorBoundary>
        </Grid>
      </Grid>
    </Card>
  );
}
