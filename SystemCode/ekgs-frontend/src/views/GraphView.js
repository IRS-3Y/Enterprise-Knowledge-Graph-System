import React from 'react';
import { makeStyles } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import Typography from '@material-ui/core/Typography';
import ErrorBoundary from '../components/core/ErrorBoundary';
import NeoVisGraph from '../components/visualization/NeoVisGraph';
import { graph as defaultConfig } from '../config';
import {NEOVIS_DEFAULT_CONFIG} from 'neovis.js/dist/neovis.js';

const useStyles = makeStyles(theme => ({
  root: {
    margin: theme.spacing(1),
    height: "100vh"
  },
  description: {
    padding: theme.spacing(3,5)
  }
}));

export default function GraphView({config = {}, cypher, description}) {
  const _config = {
    labels: {},
    relationships: {},
    ...defaultConfig,
    ...config
  };
  const classes = useStyles();
  return (
    <Card className={classes.root}>
      <Grid>
        {description && description.length? (
          <Grid item>
            <div className={classes.description}>
              {description.map((desc, i) => <Typography key={i} variant="body1">{desc}</Typography>)}
            </div>
          </Grid>
        ): null}
        <Grid item>
          <ErrorBoundary>
            <NeoVisGraph config={{
                labels: {
                  [NEOVIS_DEFAULT_CONFIG]: {
                    caption: _config.nodeCaption
                  },
                  ..._config.labels
                },
                relationships: {
                  [NEOVIS_DEFAULT_CONFIG]: {
                    caption: _config.relationshipCaption
                  },
                  ..._config.relationships
                }
              }}
              cypher={cypher}/>
          </ErrorBoundary>
        </Grid>
      </Grid>
    </Card>
  );
}
