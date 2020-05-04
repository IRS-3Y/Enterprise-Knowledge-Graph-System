import React from 'react';
import { makeStyles } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import ErrorBoundary from '../components/core/ErrorBoundary';
import NeoVisGraph from '../components/visualization/NeoVisGraph';
import { graph as defaultConfig } from '../config';
import {NEOVIS_DEFAULT_CONFIG} from 'neovis.js/dist/neovis.js';

const useStyles = makeStyles(theme => ({
  root: {
    margin: theme.spacing(1),
    minHeight: "90vh"
  },
  description: {
    padding: theme.spacing(5,5,0,5)
  },
  descriptionLine: {
    padding: theme.spacing(0,0,1,0)
  },
  table: {
    padding: theme.spacing(0,5)
  }
}));

export default function GraphView({config = {}, cypher, table, description}) {
  const _config = {
    labels: {},
    relationships: {},
    ...defaultConfig,
    ...config
  };
  const classes = useStyles();
  return (
    <Paper className={classes.root}>
      <Grid>
        {table && table.rows && table.rows.length? (
          <Grid item>
            {table.description && table.description.length? (
              <div className={classes.description}>
                {table.description.map((desc, i) => (
                  <Typography key={i} variant="body1" className={classes.descriptionLine}>{desc}</Typography>
                ))}
              </div>
            ): null}
            <TableContainer className={classes.table}>
              <Table>
                <TableHead>
                  <TableRow>
                    {table.columns.map(({name, label, align}) => (
                      <TableCell key={name} align={align?align:"left"}>{label}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {table.rows.map((row) => (
                    <TableRow key={row.id}>
                      {table.columns.map(({name, align, dp}) => (
                        <TableCell key={name} align={align?align:"left"}>{dp? row[name].toFixed(dp): row[name]}</TableCell>
                      ))}
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Grid>
        ): null}
        {description && description.length? (
          <Grid item>
            <div className={classes.description}>
              {description.map((desc, i) => (
                <Typography key={i} variant="body1" className={classes.descriptionLine}>{desc}</Typography>
              ))}
            </div>
          </Grid>
        ): null}
        {cypher? (
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
                cypher={cypher}
                size={table? "medium": "default"}/>
            </ErrorBoundary>
          </Grid>
        ): null}
      </Grid>
    </Paper>
  );
}
