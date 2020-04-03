import React from 'react';
import { fade, makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import Grid from '@material-ui/core/Grid';
import Autocomplete from '@material-ui/lab/Autocomplete';
import SearchIcon from '@material-ui/icons/Search';
import SearchService from '../../services/SearchService';

const backend = new SearchService();

const useStyles = makeStyles(theme => ({
  root: {
    marginLeft: theme.spacing(2)
  },
  searchInput: {
    color: 'inherit',
  },
  searchTextField: {
    width: '100%',
    [theme.breakpoints.up('lg')]: {
      width: 900,
    },
    backgroundColor: fade(theme.palette.common.white, 0.1),
    '&:hover': {
      backgroundColor: fade(theme.palette.common.white, 0.2),
    },
    borderRadius: theme.shape.borderRadius
  }
}));

export default function SearchBar({onSearchResult}) {
  const classes = useStyles();
  const [inputValue, setInputValue] = React.useState('');
  const [options, setOptions] = React.useState([]);

  const handleChange = event => {
    setInputValue(event.target.value);
  };

  React.useEffect(() => {
    let active = true;
    backend.searchSuggestionThrottled({ value: inputValue }).then(results => {
      if (active) {
        setOptions(results || []);
      }
    });
    return () => {
      active = false;
    };
  }, [inputValue]);

  return (
    <Autocomplete
      className = {classes.root}
      autoComplete
      freeSolo
      includeInputInList
      renderInput = {params => (
        <TextField {...params}
          classes={{
            root: classes.searchTextField
          }}
          variant="outlined"
          placeholder="Search…"
          InputProps={{
            ...params.InputProps,
            classes: {
              root: classes.searchInput
            },
            startAdornment: (
              <InputAdornment>
                <SearchIcon />
              </InputAdornment>
            )
          }}
          onChange={handleChange}
        />
      )}
      options={options}
      getOptionLabel={option => (typeof option === 'string' ? option : option.description)}
      renderOption={option => {
        return (
          <Grid container alignItems="center">
            <Grid item xs>
              <span>{option}</span>
            </Grid>
          </Grid>
        );
      }}
      onClose = {
        e => backend.searchAction({value: e.target.value}).then(onSearchResult)
      }
    />
  );
}
