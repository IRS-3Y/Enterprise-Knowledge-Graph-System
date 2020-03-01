import React from "react";
import { components, styles } from "@lib/material-dashboard-react";
import { makeStyles } from "@material-ui/core/styles";
import Search from "@material-ui/icons/Search";

const {Input: CustomInput, Button} = components;
const {jss: {whiteColor}} = styles;

const useStyles = makeStyles(theme => ({
  margin: {
    zIndex: "4",
    margin: "0"
  },
  search: {
    "& > div": {
      marginTop: "0"
    },
    [theme.breakpoints.down("sm")]: {
      margin: "10px 15px !important",
      float: "none !important",
      paddingTop: "1px",
      paddingBottom: "1px",
      padding: "0!important",
      width: "60%",
      marginTop: "40px",
      "& input": {
        color: whiteColor
      }
    }
  },
  searchWrapper: {
    [theme.breakpoints.down("sm")]: {
      width: "-webkit-fill-available",
      margin: "10px 15px 0"
    },
    display: "inline-block"
  }
}));

export default function NavbarLinks() {
  const classes = useStyles();
  return (
    <div>
      <div className={classes.searchWrapper}>
        <CustomInput
          formControlProps={{
            className: classes.margin + " " + classes.search
          }}
          inputProps={{
            placeholder: "Search",
            inputProps: {
              "aria-label": "Search"
            }
          }}
        />
        <Button color="white" aria-label="edit" justIcon round>
          <Search />
        </Button>
      </div>
    </div>
  );
}
