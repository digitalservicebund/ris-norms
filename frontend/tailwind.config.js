/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,vue,js,ts}"],
  presets: [require("@digitalservice4germany/style-dictionary/tailwind")],
  plugins: [require("@digitalservice4germany/angie")],
  theme: {
    extend: {
      colors: {
        highlight: {
          mod: {
            hover: "rgba(196, 196, 196, 0.6)",
            selected: "rgba(224, 244, 255, 1)",
            default: "rgba(231, 231, 231, 0.4)",
            border: "#4299F7",
          },
          affectedDocument: {
            hover: "rgba(255, 154, 251, 0.4)",
            selected: "rgba(255, 154, 251, 1)",
            default: "rgba(255, 154, 251, 0.2)",
            border: "#FE4AF7",
          },
        },
      },
    },
  },
}
