import "./style.css"

const app = document.querySelector<HTMLElement>("#app")

if (app)
  app.innerHTML = `
  <h1>Hello DigitalService!</h1>
  <a href="https://vitejs.dev/guide/features.html" target="_blank">Vite Documentation</a>
`
