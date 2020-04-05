import React, {useEffect} from 'react';
import { makeStyles } from "@material-ui/core/styles";
import NeoVis, {NEOVIS_DEFAULT_CONFIG} from 'neovis.js/dist/neovis.js';
import config from '../../config';

const useStyles = makeStyles(theme => ({
  NeoVisGraph: {
    width: "100%",
    height: "100vh",
    font: "22pt arial"
  }
}));

export default function NeoVisGraph({cypher}) {
  useEffect(() => {
    new NeoVis({
      container_id: "viz",
      server_url: config.graph.serverUrl,
      labels: {
        [NEOVIS_DEFAULT_CONFIG]: {
          caption: "name"
        }
      },
      relationships: {
        [NEOVIS_DEFAULT_CONFIG]: {
          caption: false
        }
      },
      initial_cypher: cypher
    })
    .render();
  });

  const classes = useStyles();
  return <div id="viz" className={classes.NeoVisGraph}/>
}
