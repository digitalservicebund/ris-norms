import { defineConfig } from "vite";

export default defineConfig({
  build: {
    lib: {
      entry: "./src/extension.ts",
      formats: ["cjs"],
      fileName: "extension",
    },
    rollupOptions: {
      external: ["vscode"],
      output: {
        globals: {
          vscode: "vscode",
        },
      },
    },
    outDir: "dist",
  },
  resolve: {
    alias: {
      "@": "./src",
      "~": "./test",
    },
  },
});
