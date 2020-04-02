import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import parse from 'autosuggest-highlight/parse';
import SearchService from '../../services/SearchService';

const backend = new SearchService();

const useStyles = makeStyles(theme => ({
  icon: {
    color: theme.palette.text.secondary,
    marginRight: theme.spacing(2),
  },
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
      style={{ width: '100%' }}
      getOptionLabel={option => (typeof option === 'string' ? option : option.description)}
      options={options}
      autoComplete
      includeInputInList
      renderInput={params => (
        <TextField
          {...params}
          label="Search"
          variant="outlined"
          fullWidth
          onChange={handleChange}
        />
      )}
      renderOption={option => {
        // const matches = option.structured_formatting.main_text_matched_substrings;
        // const parts = parse(
        //   option.structured_formatting.main_text,
        //   matches.map(match => [match.offset, match.offset + match.length]),
        // );

        return (
          <Grid container alignItems="center">
            {/* <Grid item>
              <LocationOnIcon className={classes.icon} />
            </Grid> */}
            <Grid item xs>
              {/* {parts.map((part, index) => (
                <span key={index} style={{ fontWeight: part.highlight ? 700 : 400 }}>
                  {part.text}
                </span>
              ))}

              <Typography variant="body2" color="textSecondary">
                {option.structured_formatting.secondary_text}
              </Typography> */}
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