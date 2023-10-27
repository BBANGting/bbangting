import { Theme, ThemeOptions } from '@mui/material';

declare module '@mui/material/styles' {
  interface Palette {
    header: Palette['primary'];
  }
  interface PaletteOptions {
    header?: PaletteOptions['primary'];
  }
  interface Palette {
    accountIcon: Palette['primary'];
  }
  interface PaletteOptions {
    accountIcon?: PaletteOptions['primary'];
  }
  interface Palette {
    button: Palette['primary'];
  }
  interface PaletteOptions {
    button?: PaletteOptions['primary'];
  }
  export function createTheme(options: ThemeOptions): Theme;
}

declare module '@mui/material/AppBar' {
  interface AppBarPropsColorOverrides {
    header: true;
  }
}

declare module '@mui/material/IconButton' {
  interface IconButtonPropsColorOverrides {
    accountIcon: true;
  }
}

declare module '@mui/material/Button' {
  interface ButtonPropsColorOverrides {
    button: true;
  }
}
