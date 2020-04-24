import React, {useEffect} from 'react';
import classnames from 'classnames';
import { makeStyles } from "@material-ui/core/styles";
import NeoVis from 'neovis.js/dist/neovis.js';
import { neovis as defaultConfig } from '../../config';

const useStyles = makeStyles(theme => ({
  NeoVisGraph: {
    width: "100%"
  },
  sizeDefault: {
    height: "90vh"
  },
  sizeMedium: {
    height: "60vh"
  }
}));

export default function NeoVisGraph({config = {}, cypher = "", size = "default"}) {
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
  return <div id="viz" className={classnames({
    [classes.NeoVisGraph]: true,
    [classes.sizeDefault]: size === 'default',
    [classes.sizeMedium]: size === 'medium'
  })}/>
}
