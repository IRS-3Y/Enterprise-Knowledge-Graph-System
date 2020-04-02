import axios from 'axios';
import _ from 'lodash';

/**
 * Client service for calling backend search REST API
 * @author TS, Yin
 */
export default class SearchService {
  constructor(){
    this._baseUrl = "/backend/search";
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

  searchAction = async ({value}) => {
    if(value){
      const name = value.substring('360 degree scan for Technology '.length);
      if(name){
        return {name};
      }
    }
    return null;
  }
  searchActionThrottled = _.throttle((input) => this.searchAction(input), 100);

  searchSuggestion = async (input) => {
    let data = await this.search(input);
    return data.results.filter(r => r.type === 'suggestion').map(r => r.value);
  }
  searchSuggestionThrottled = _.throttle((input) => this.searchSuggestion(input), 100);
  
}