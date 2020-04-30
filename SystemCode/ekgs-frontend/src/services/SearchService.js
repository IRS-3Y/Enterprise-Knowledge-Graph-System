import axios from 'axios';
import _ from 'lodash';
import config from '../config';

/**
 * Client service for calling backend search REST API
 * @author TS, Yin
 */
export default class SearchService {
  constructor(){
    this._baseUrl = `${config.backend.baseUrl}/search`;
  }

  search = async (input, subUrl = "") => {
    if(!input){
      input = {value: ""};
    }else if(typeof input === 'string'){
      input = {value: input};
    }
    let resp = await axios.post(this._baseUrl + subUrl, input);
    return resp.data;
  }

  searchAction = async (input) => {
    let data = await this.search(input);
    let actions = data.results.filter(r => r.type === 'action');
    if(!actions.length){
      //retry with dialogflow query
      data = await this.search(input, "/dialog");
      actions = data.results.filter(r => r.type === 'action');
    }
    return actions;
  }
  searchActionThrottled = _.throttle((input) => this.searchAction(input), 100);

  searchSuggestion = async (input) => {
    let data = await this.search(input);
    return data.results.filter(r => r.type === 'suggestion').map(r => r.value);
  }
  searchSuggestionThrottled = _.throttle((input) => this.searchSuggestion(input), 100);
  
}