import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/logout': 'http://localhost:8080',
      '/auth': 'http://localhost:8080',
    },
  },
});
