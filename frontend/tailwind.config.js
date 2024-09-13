const {
  RisUiPlugin,
  RisUiPreset,
} = require("@digitalservicebund/ris-ui/tailwind")

const angie = require("@digitalservice4germany/angie")

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,vue,js,ts}"],

  presets: [RisUiPreset],

  plugins: [RisUiPlugin, angie],

  theme: {
    extend: {
      colors: {
        highlight: {
          default: {
            hover: "#94E8FE",
            selected: "#94E8FE",
            default: "#D6F7FE",
          },
          1: {
            default: "#D0F1F5",
            hover: "#88D7E3",
            selected: "#88D7E3",
          },
          2: {
            default: "#FEF4CA",
            hover: "#FAE17A",
            selected: "#FAE17A",
          },
          3: {
            default: "#EAD9FC",
            hover: "#C69CF8",
            selected: "#C69CF8",
          },
          4: {
            default: "#DDFDD8",
            hover: "#A7F998",
            selected: "#A7F998",
          },
          5: {
            default: "#FEE8D2",
            hover: "#FAC08F",
            selected: "#FAC08F",
          },
          6: {
            default: "#D6F7FE",
            hover: "#94E8FE",
            selected: "#94E8FE",
          },
          7: {
            default: "#FDDDDD",
            hover: "#FAA6A6",
            selected: "#FAA6A6",
          },
          8: {
            default: "#E0E0FB",
            hover: "#ADADF3",
            selected: "#ADADF3",
          },
          9: {
            default: "#D2FBF0",
            hover: "#8BF2D6",
            selected: "#8BF2D6",
          },
          10: {
            default: "#D8E6F2",
            hover: "#9DBBDC",
            selected: "#9DBBDC",
          },
          affectedDocument: {
            default: "#FFEBFE", // Light pink for default state
            hover: "#FFB7FB", // Brighter pink for hover state
            selected: "#FFB7FB", // Same brighter pink for selected state
          },
          quotedStructure: {
            hover: {
              background: "#e5f7ff",
              border: "#4299f7",
              innerHover: {
                background: "#b9e8ff",
                border: "#4299f7",
              },
            },
            selected: {
              content: "#b9e8ff",
              border: "#004b76",
              pseudo: {
                background: "#78ccff",
                border: "#004b76",
              },
            },
          },
          elementSelect: {
            default: {
              border: "#004b76",
            },
            selected: {
              background: "#B0EFFE",
              border: "#000",
            },
            hover: {
              background: "#D6F7FE",
              border: "#004B76",
            },
          },
        },
      },

      fontFamily: {
        mono: [
          "ui-monospace",
          "Cascadia Code",
          "Source Code Pro",
          "Menlo",
          "Consolas",
          "DejaVu Sans Mono",
          "monospace",
        ],
      },
    },
  },

  safelist: [
    // classes used dynamically by useModHighlightClasses
    {
      pattern: /bg-highlight-(\d+|default)-(default|hover|selected)/,
      variants: ["hover", "focus"],
    },
    {
      pattern: /border-highlight-(\d+|default)-selected/,
    },
    "border",
    "px-2",
    "border-dotted",
    "border-gray-900",
    "border-solid",
  ],
}
