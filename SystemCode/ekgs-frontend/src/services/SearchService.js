import axios from 'axios';
import _ from 'lodash';
import config from '../config';
import AppService from './AppService';

const app = new AppService();

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

  searchAction = async (input, dialog) => {
    let data = await this.search(input);
    let actions = data.results.filter(r => r.type === 'action');
    if(!actions.length && dialog){
      try{
        //retry with dialogflow query
        data = await this.search(input, "/dialog");
        actions = data.results.filter(r => r.type === 'action');
      }catch(e){
        console.error(e);
        app.checkStatus();
      }
    }
    return actions;
  }
  searchActionThrottled = _.throttle((input) => this.searchAction(input), 100);

  searchSuggestion = async (input) => {
    let data = await this.search(input);
    return data.results.filter(r => r.type === 'suggestion').map(r => r.value);
  }
  searchSuggestionThrottled = _.throttle((input) => this.searchSuggestion(input), 100);
  
  getHistory = (limit = 20, includes = "", excludes = []) => {
    let pattern = includes.toLowerCase();
    let history = window.localStorage.getItem('ekgs-search-history');
    return history? JSON.parse(history).filter(v => v.toLowerCase().includes(pattern) && !excludes.includes(v)).slice(0, limit): [];
  }

  addHistory = (value) => {
    let history = this.getHistory();
    history = history.filter(h => h !== value);
    history.unshift(value);
    history = history.slice(0, 20);
    window.localStorage.setItem('ekgs-search-history', JSON.stringify(history));
  }
}