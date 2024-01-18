/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,vue,js,ts}"],
  presets: [require("@digitalservice4germany/style-dictionary/tailwind")],
  plugins: [require("@digitalservice4germany/angie")],
}
