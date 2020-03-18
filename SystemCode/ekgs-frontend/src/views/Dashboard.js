import React, {useState} from 'react';
import { components } from '@lib/material-dashboard-react';
import NeoVisGraph from '../components/visualization/NeoVisGraph';
import ErrorBoundary from '../components/core/ErrorBoundary';
import { makeStyles } from '@material-ui/core';

const {Card, CardHeader, CardBody, CardFooter, Input} = components;

const useStyles = makeStyles(theme => ({
  root: {
    margin: 0
  },
  cardTitleWhite: {
    color: "#FFFFFF",
    marginTop: "0px",
    minHeight: "auto",
    fontWeight: "300",
    fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
    marginBottom: "3px",
    textDecoration: "none"
  },
  cardSubtitleWhite: {
    color: "rgba(255,255,255,.62)",
    margin: "0",
    fontSize: "14px",
    marginTop: "0",
    marginBottom: "0"
  },
}));

export default function Dashboard() {
  const classes = useStyles();
  const title = "Cypher Query";
  const subtitle = null;
  const color ="info";
  const [cypher, setCypher] = useState("MATCH(n)-[r]->(m) RETURN *");
  return (
    <Card className={classes.root}>
      <CardHeader color={color}>
        {title? <h4 className={classes.cardTitleWhite}>{title}</h4>: null}
        {subtitle? <p className={classes.cardSubtitleWhite}>{subtitle}</p>: null}
      </CardHeader>
      <CardBody>
        <ErrorBoundary>
          <NeoVisGraph cypher={cypher}/>
        </ErrorBoundary>
      </CardBody>
      <CardFooter>
        <Input labelText="Query" 
          formControlProps={{fullWidth: true}}
          inputProps={{
            value: cypher,
            onChange: e => setCypher(e.target.value)
          }}/>
      </CardFooter>
    </Card>
  );
}
