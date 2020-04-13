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

  search = async (input) => {
    if(!input){
      input = {value: ""};
    }else if(typeof input === 'string'){
      input = {value: input};
    }
    let resp = await axios.post(this._baseUrl, input);
    return resp.data;
  }

  searchAction = async (input) => {
    let data = await this.search(input);
    return data.results.filter(r => r.type === 'action');
  }
  searchActionThrottled = _.throttle((input) => this.searchAction(input), 100);

  searchSuggestion = async (input) => {
    let data = await this.search(input);
    return data.results.filter(r => r.type === 'suggestion').map(r => r.value);
  }
  searchSuggestionThrottled = _.throttle((input) => this.searchSuggestion(input), 100);
  
}