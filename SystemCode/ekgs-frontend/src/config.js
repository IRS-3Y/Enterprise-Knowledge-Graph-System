import {NEOVIS_DEFAULT_CONFIG} from 'neovis.js/dist/neovis.js';

const backend = {
  baseUrl: '/backend'
};

const graph = {
  nodeCaption: "name",
  relationshipCaption: false
};

const neovis = {
  server_url: `bolt://${window.location.hostname}:7687`,
  labels: {
    [NEOVIS_DEFAULT_CONFIG]: {
      caption: graph.nodeCaption
    }
  },
  relationships: {
    [NEOVIS_DEFAULT_CONFIG]: {
      caption: graph.relationshipCaption
    }
  }
};

export {backend, graph, neovis};
export default {backend, graph, neovis};