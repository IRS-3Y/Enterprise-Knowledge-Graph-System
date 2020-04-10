import React, {useEffect} from 'react';
import { makeStyles } from "@material-ui/core/styles";
import NeoVis from 'neovis.js/dist/neovis.js';
import { neovis as defaultConfig } from '../../config';

const useStyles = makeStyles(theme => ({
  NeoVisGraph: {
    width: "100%",
    height: "100vh",
    font: "22pt arial"
  }
}));

export default function NeoVisGraph({config = {}, cypher = ""}) {
  useEffect(() => {
    new NeoVis({
      ...defaultConfig,
      ...config,
      container_id: "viz",
      initial_cypher: cypher
    })
    .render();
  });

  const classes = useStyles();
  return <div id="viz" className={classes.NeoVisGraph}/>
}
