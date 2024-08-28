# 13. Organize feature specific things by page in frontend

Date: 2024-08-22

## Status

Accepted

## Context

Our project grows and both the `components` and `composables` folders grow further and further. With this it becomes
harder to find things related to specific features.

Currently, our folder structure organizes things exclusively by its type and with no further (agreed upon) organization:

```
src
├── assets
├── components
│  └── controls
├── composables
├── lib
├── services
├── types
└── views
```

With this structure it is also not clear which things (components, composables, ...) are developed to be shared between
multiple places and which are one-of solutions for a specific feature. Also almost every feature needs to touch many
different places in the code.
We therefore, want to increase the colocation of things related to the same feature and make clear which things are
shared and used by multiple features.

At the moment all our big features have their own route[^1] in our application.

## Decision

We use the existing `components` and `composables` folders for shared components and composables.

The `views` folder gets restructured to also contain things that are specific to the specific route of that view.

The view folders are organized by the url paths of the view.

```
src
├── assets
├── components
├── composables
├── lib
├── services
├── types
└── views
    ├── 404
    ├── amending-law
    │   ├── affected-documents
    │   │   ├── metadata-editor
    │   │   │   ├── element
    │   │   │   └── rahmen
    │   │   └── references
    │   ├── articles
    │   │   └── editor
    │   │       ├── multiple-mods
    │   │       └── single-mod
    │   ├── overview
    │   ├── publishing
    │   └── temporal-data
    └── amending-laws
```

Each folder for a view contains the component for the view as well as everything that is specific to that view or other
nested views. This means that something in `single-mod` can access a composable from `articles` but something
from `affected-documents` is not allowed that access. Or to phrase it differently: Access to parents is allowed but
access to siblings and children is not.

The view component itself uses the file extension `.view.vue` to differentiate it from view specific components.

## Consequences

With this change it becomes easier to locate view specific things. But it is no longer that simple to find e.g. all
composables, so it is less clear if a similar composable already exists. But as only non-shared things are organized
next to the views this should only impact finding things that are probably not reusable immediately anyway.

By organizing the `views` folder by the url-paths of the pages it also becomes very simple to find the code for a
specific part of the app you have currently opened.

There is some risk that we need to reorganize things again if/ when the `lib`, `service` or `types` folders grow too
large as they probably do not sort that well into specific views. Maybe we will than also need to organize some shared
things by the entity (norm, announcement, temporal-data, ...) that are affected by them. But this should not affect the
things that are organized under specific views.
Some further risk is that certain views grow so large that a flat file structure is no longer enough to quickly find
something and we need to introduce some further nesting there.
It could also be the case that we add some larger features that are not view-specific. In that case we could still add
some `features` folder to organize everything related to that feature.

Also the urls of our application could change and with this proposale they would be tightly coupled.

## Further details

<details>
  <summary>Current folder structure:</summary>

```
src
├── App.vue
├── assets
│   └── neuRIS-logo.svg
├── components
│   ├── RisEmptyState.spec.ts
│   ├── RisEmptyState.story.vue
│   ├── RisEmptyState.vue
│   ├── RisLawPreview.spec.ts
│   ├── RisLawPreview.story.vue
│   ├── RisLawPreview.vue
│   ├── RisModForm.spec.ts
│   ├── RisModForm.story.vue
│   ├── RisModForm.vue
│   ├── RisTemporalDataIntervals.spec.ts
│   ├── RisTemporalDataIntervals.story.vue
│   ├── RisTemporalDataIntervals.vue
│   ├── affectedDocuments
│   │   ├── RisAffectedDocumentPanel.spec.ts
│   │   └── RisAffectedDocumentPanel.vue
│   ├── amendingLaws
│   │   ├── RisAmendingLawCard.spec.ts
│   │   ├── RisAmendingLawCard.story.vue
│   │   └── RisAmendingLawCard.vue
│   ├── controls
│   │   ├── RisAlert.spec.ts
│   │   ├── RisAlert.story.vue
│   │   ├── RisAlert.vue
│   │   ├── RisCallout.spec.ts
│   │   ├── RisCallout.story.vue
│   │   ├── RisCallout.vue
│   │   ├── RisCheckboxInput.spec.ts
│   │   ├── RisCheckboxInput.story.vue
│   │   ├── RisCheckboxInput.vue
│   │   ├── RisCopyableLabel.spec.ts
│   │   ├── RisCopyableLabel.story.vue
│   │   ├── RisCopyableLabel.vue
│   │   ├── RisDateInput.spec.ts
│   │   ├── RisDateInput.story.vue
│   │   ├── RisDateInput.vue
│   │   ├── RisDropdownInput.spec.ts
│   │   ├── RisDropdownInput.story.vue
│   │   ├── RisDropdownInput.vue
│   │   ├── RisErrorCallout.spec.ts
│   │   ├── RisErrorCallout.story.vue
│   │   ├── RisErrorCallout.vue
│   │   ├── RisHeader.spec.ts
│   │   ├── RisHeader.story.vue
│   │   ├── RisHeader.vue
│   │   ├── RisInfoHeader.spec.ts
│   │   ├── RisInfoHeader.story.vue
│   │   ├── RisInfoHeader.vue
│   │   ├── RisInfoModal.spec.ts
│   │   ├── RisInfoModal.story.vue
│   │   ├── RisInfoModal.vue
│   │   ├── RisLoadingSpinner.spec.ts
│   │   ├── RisLoadingSpinner.story.vue
│   │   ├── RisLoadingSpinner.vue
│   │   ├── RisNavbar.spec.ts
│   │   ├── RisNavbar.story.vue
│   │   ├── RisNavbar.vue
│   │   ├── RisNavbarSide.spec.ts
│   │   ├── RisNavbarSide.story.vue
│   │   ├── RisNavbarSide.vue
│   │   ├── RisRadioInput.spec.ts
│   │   ├── RisRadioInput.story.vue
│   │   ├── RisRadioInput.vue
│   │   ├── RisTextAreaInput.spec.ts
│   │   ├── RisTextAreaInput.story.vue
│   │   ├── RisTextAreaInput.vue
│   │   ├── RisTextButton.spec.ts
│   │   ├── RisTextButton.story.vue
│   │   ├── RisTextButton.vue
│   │   ├── RisTextInput.spec.ts
│   │   ├── RisTextInput.story.vue
│   │   ├── RisTextInput.vue
│   │   ├── RisTooltip.spec.ts
│   │   ├── RisTooltip.story.vue
│   │   └── RisTooltip.vue
│   ├── editor
│   │   ├── RisCodeEditor.spec.ts
│   │   ├── RisCodeEditor.story.vue
│   │   ├── RisCodeEditor.vue
│   │   ├── RisTabs.spec.ts
│   │   ├── RisTabs.story.vue
│   │   ├── RisTabs.vue
│   │   └── composables
│   │       ├── useCodemirrorVueEditableExtension.spec.ts
│   │       ├── useCodemirrorVueEditableExtension.ts
│   │       ├── useCodemirrorVueReadonlyExtension.spec.ts
│   │       └── useCodemirrorVueReadonlyExtension.ts
│   └── references
│       ├── RisModRefsEditor.spec.ts
│       ├── RisModRefsEditor.vue
│       ├── RisModSelectionPanel.spec.ts
│       ├── RisModSelectionPanel.vue
│       ├── RisRefEditor.spec.ts
│       ├── RisRefEditor.vue
│       ├── RisRefEditorTable.spec.ts
│       ├── RisRefEditorTable.vue
│       ├── RisRefSelectionPanel.spec.ts
│       └── RisRefSelectionPanel.vue
├── composables
│   ├── referencesService.spec.ts
│   ├── useAffectedDocuments.spec.ts
│   ├── useAffectedDocuments.ts
│   ├── useAknTextSelection.spec.ts
│   ├── useAknTextSelection.ts
│   ├── useAmendingLawRelease.spec.ts
│   ├── useAmendingLawRelease.ts
│   ├── useArticle.spec.ts
│   ├── useArticle.ts
│   ├── useEIdRange.spec.ts
│   ├── useEIdRange.ts
│   ├── useEidPathParameter.spec.ts
│   ├── useEidPathParameter.ts
│   ├── useElementId.spec.ts
│   ├── useElementId.ts
│   ├── useEliPathParameter.spec.ts
│   ├── useEliPathParameter.ts
│   ├── useMod.spec.ts
│   ├── useMod.ts
│   ├── useModEidPathParameter.spec.ts
│   ├── useModEidPathParameter.ts
│   ├── useModEidSelection.spec.ts
│   ├── useModEidSelection.ts
│   ├── useModHighlightClasses.spec.ts
│   ├── useModHighlightClasses.ts
│   ├── useMods.spec.ts
│   ├── useMods.ts
│   ├── useMultiSelection.spec.ts
│   ├── useMultiSelection.ts
│   ├── useNormRender.spec.ts
│   ├── useNormRender.ts
│   ├── useNormXml.spec.ts
│   ├── useNormXml.ts
│   ├── useRef.spec.ts
│   ├── useRef.ts
│   ├── useSentryTraceId.ts
│   ├── useTemporalData.spec.ts
│   ├── useTemporalData.ts
│   ├── useTimeBoundaryPathParameter.spec.ts
│   └── useTimeBoundaryPathParameter.ts
├── histoire-setup.ts
├── lib
│   ├── frbr.spec.ts
│   ├── frbr.ts
│   ├── htmlRangeToLdmlDeRange.spec.ts
│   ├── htmlRangeToLdmlDeRange.ts
│   ├── proprietary.spec.ts
│   ├── proprietary.ts
│   ├── ref.spec.ts
│   └── ref.ts
├── main.ts
├── router.ts
├── services
│   ├── announcementReleaseService.spec.ts
│   ├── announcementReleaseService.ts
│   ├── announcementService.spec.ts
│   ├── announcementService.ts
│   ├── apiService.spec.ts
│   ├── apiService.ts
│   ├── articleService.spec.ts
│   ├── articleService.ts
│   ├── elementService.spec.ts
│   ├── elementService.ts
│   ├── ldmldeEventRefService.spec.ts
│   ├── ldmldeEventRefService.ts
│   ├── ldmldeModService.spec.ts
│   ├── ldmldeModService.ts
│   ├── ldmldeService.spec.ts
│   ├── ldmldeService.ts
│   ├── ldmldeTemporalGroupService.spec.ts
│   ├── ldmldeTemporalGroupService.ts
│   ├── ldmldeTextualModService.spec.ts
│   ├── ldmldeTextualModService.ts
│   ├── normService.spec.ts
│   ├── normService.ts
│   ├── proprietaryService.spec.ts
│   ├── proprietaryService.ts
│   ├── referencesService.ts
│   ├── temporalDataService.spec.ts
│   ├── temporalDataService.ts
│   ├── xmlService.spec.ts
│   └── xmlService.ts
├── style.css
├── types
│   ├── ModType.ts
│   ├── amendingLawRelease.ts
│   ├── article.ts
│   ├── element.ts
│   ├── lawElementIdentifier.ts
│   ├── norm.ts
│   ├── proprietary.ts
│   ├── temporalDataResponse.ts
│   └── validationError.ts
├── views
│   ├── 404NotFound.vue
│   ├── AffectedDocuments.vue
│   ├── AmendingLaw.vue
│   ├── AmendingLawArticleEditor.vue
│   ├── AmendingLawArticleEditorMultiMod.vue
│   ├── AmendingLawArticleEditorSingleMod.vue
│   ├── AmendingLawMetadataEditor.vue
│   ├── AmendingLawMetadataEditorElement.vue
│   ├── AmendingLawMetadataEditorRahmen.vue
│   ├── AmendingLawOverview.vue
│   ├── AmendingLaws.vue
│   ├── Articles.vue
│   ├── Publishing.vue
│   ├── References.vue
│   └── TemporalData.vue
├── vite-env.d.ts
└── vitest-setup.ts

14 directories, 200 files
```

</details>

<details>
  <summary>New folder structure:</summary>

```
src
├── App.vue
├── assets
│   └── neuRIS-logo.svg
├── components
│   ├── RisEmptyState.spec.ts
│   ├── RisEmptyState.story.vue
│   ├── RisEmptyState.vue
│   ├── RisLawPreview.spec.ts
│   ├── RisLawPreview.story.vue
│   ├── RisLawPreview.vue
│   ├── controls
│   │   ├── RisAlert.spec.ts
│   │   ├── RisAlert.story.vue
│   │   ├── RisAlert.vue
│   │   ├── RisCallout.spec.ts
│   │   ├── RisCallout.story.vue
│   │   ├── RisCallout.vue
│   │   ├── RisCheckboxInput.spec.ts
│   │   ├── RisCheckboxInput.story.vue
│   │   ├── RisCheckboxInput.vue
│   │   ├── RisCopyableLabel.spec.ts
│   │   ├── RisCopyableLabel.story.vue
│   │   ├── RisCopyableLabel.vue
│   │   ├── RisDateInput.spec.ts
│   │   ├── RisDateInput.story.vue
│   │   ├── RisDateInput.vue
│   │   ├── RisDropdownInput.spec.ts
│   │   ├── RisDropdownInput.story.vue
│   │   ├── RisDropdownInput.vue
│   │   ├── RisErrorCallout.spec.ts
│   │   ├── RisErrorCallout.story.vue
│   │   ├── RisErrorCallout.vue
│   │   ├── RisHeader.spec.ts
│   │   ├── RisHeader.story.vue
│   │   ├── RisHeader.vue
│   │   ├── RisInfoHeader.spec.ts
│   │   ├── RisInfoHeader.story.vue
│   │   ├── RisInfoHeader.vue
│   │   ├── RisInfoModal.spec.ts
│   │   ├── RisInfoModal.story.vue
│   │   ├── RisInfoModal.vue
│   │   ├── RisLoadingSpinner.spec.ts
│   │   ├── RisLoadingSpinner.story.vue
│   │   ├── RisLoadingSpinner.vue
│   │   ├── RisNavbar.spec.ts
│   │   ├── RisNavbar.story.vue
│   │   ├── RisNavbar.vue
│   │   ├── RisNavbarSide.spec.ts
│   │   ├── RisNavbarSide.story.vue
│   │   ├── RisNavbarSide.vue
│   │   ├── RisRadioInput.spec.ts
│   │   ├── RisRadioInput.story.vue
│   │   ├── RisRadioInput.vue
│   │   ├── RisTextAreaInput.spec.ts
│   │   ├── RisTextAreaInput.story.vue
│   │   ├── RisTextAreaInput.vue
│   │   ├── RisTextButton.spec.ts
│   │   ├── RisTextButton.story.vue
│   │   ├── RisTextButton.vue
│   │   ├── RisTextInput.spec.ts
│   │   ├── RisTextInput.story.vue
│   │   ├── RisTextInput.vue
│   │   ├── RisTooltip.spec.ts
│   │   ├── RisTooltip.story.vue
│   │   └── RisTooltip.vue
│   └── editor
│       ├── RisCodeEditor.spec.ts
│       ├── RisCodeEditor.story.vue
│       ├── RisCodeEditor.vue
│       ├── RisTabs.spec.ts
│       ├── RisTabs.story.vue
│       ├── RisTabs.vue
│       └── composables
│           ├── useCodemirrorVueEditableExtension.spec.ts
│           ├── useCodemirrorVueEditableExtension.ts
│           ├── useCodemirrorVueReadonlyExtension.spec.ts
│           └── useCodemirrorVueReadonlyExtension.ts
├── composables
│   ├── referencesService.spec.ts
│   ├── useAknTextSelection.spec.ts
│   ├── useAknTextSelection.ts
│   ├── useEIdRange.spec.ts
│   ├── useEIdRange.ts
│   ├── useEidPathParameter.spec.ts
│   ├── useEidPathParameter.ts
│   ├── useElementId.spec.ts
│   ├── useElementId.ts
│   ├── useEliPathParameter.spec.ts
│   ├── useEliPathParameter.ts
│   ├── useModHighlightClasses.spec.ts
│   ├── useModHighlightClasses.ts
│   ├── useMultiSelection.spec.ts
│   ├── useMultiSelection.ts
│   ├── useNormRender.spec.ts
│   ├── useNormRender.ts
│   ├── useNormXml.spec.ts
│   ├── useNormXml.ts
│   ├── useSentryTraceId.ts
│   ├── useTemporalData.spec.ts
│   └── useTemporalData.ts
├── histoire-setup.ts
├── lib
│   ├── frbr.spec.ts
│   ├── frbr.ts
│   ├── htmlRangeToLdmlDeRange.spec.ts
│   ├── htmlRangeToLdmlDeRange.ts
│   ├── proprietary.spec.ts
│   ├── proprietary.ts
│   ├── ref.spec.ts
│   └── ref.ts
├── main.ts
├── router.ts
├── services
│   ├── announcementReleaseService.spec.ts
│   ├── announcementReleaseService.ts
│   ├── announcementService.spec.ts
│   ├── announcementService.ts
│   ├── apiService.spec.ts
│   ├── apiService.ts
│   ├── articleService.spec.ts
│   ├── articleService.ts
│   ├── elementService.spec.ts
│   ├── elementService.ts
│   ├── ldmldeEventRefService.spec.ts
│   ├── ldmldeEventRefService.ts
│   ├── ldmldeModService.spec.ts
│   ├── ldmldeModService.ts
│   ├── ldmldeService.spec.ts
│   ├── ldmldeService.ts
│   ├── ldmldeTemporalGroupService.spec.ts
│   ├── ldmldeTemporalGroupService.ts
│   ├── ldmldeTextualModService.spec.ts
│   ├── ldmldeTextualModService.ts
│   ├── normService.spec.ts
│   ├── normService.ts
│   ├── proprietaryService.spec.ts
│   ├── proprietaryService.ts
│   ├── referencesService.ts
│   ├── temporalDataService.spec.ts
│   ├── temporalDataService.ts
│   ├── xmlService.spec.ts
│   └── xmlService.ts
├── style.css
├── types
│   ├── ModType.ts
│   ├── amendingLawRelease.ts
│   ├── article.ts
│   ├── element.ts
│   ├── lawElementIdentifier.ts
│   ├── norm.ts
│   ├── proprietary.ts
│   ├── temporalDataResponse.ts
│   └── validationError.ts
├── views
│   ├── 404
│   │   └── 404NotFound.view.vue
│   ├── amending-law
│   │   ├── AmendingLaw.view.vue
│   │   ├── affected-documents
│   │   │   ├── AffectedDocuments.view.vue
│   │   │   ├── RisAffectedDocumentPanel.spec.ts
│   │   │   ├── RisAffectedDocumentPanel.vue
│   │   │   ├── metadata-editor
│   │   │   │   ├── AmendingLawMetadataEditor.view.vue
│   │   │   │   ├── element
│   │   │   │   │   └── AmendingLawMetadataEditorElement.view.vue
│   │   │   │   ├── rahmen
│   │   │   │   │   └── AmendingLawMetadataEditorRahmen.view.vue
│   │   │   │   ├── useTimeBoundaryPathParameter.spec.ts
│   │   │   │   └── useTimeBoundaryPathParameter.ts
│   │   │   ├── references
│   │   │   │   ├── References.view.vue
│   │   │   │   ├── RisModRefsEditor.spec.ts
│   │   │   │   ├── RisModRefsEditor.vue
│   │   │   │   ├── RisModSelectionPanel.spec.ts
│   │   │   │   ├── RisModSelectionPanel.vue
│   │   │   │   ├── RisRefEditor.spec.ts
│   │   │   │   ├── RisRefEditor.vue
│   │   │   │   ├── RisRefEditorTable.spec.ts
│   │   │   │   ├── RisRefEditorTable.vue
│   │   │   │   ├── RisRefSelectionPanel.spec.ts
│   │   │   │   ├── RisRefSelectionPanel.vue
│   │   │   │   ├── useRef.spec.ts
│   │   │   │   └── useRef.ts
│   │   │   ├── useAffectedDocuments.spec.ts
│   │   │   └── useAffectedDocuments.ts
│   │   ├── articles
│   │   │   ├── Articles.view.vue
│   │   │   └── editor
│   │   │       ├── AmendingLawArticleEditor.view.vue
│   │   │       ├── multiple-mods
│   │   │       │   ├── AmendingLawArticleEditorMultiMod.view.vue
│   │   │       │   ├── useMods.spec.ts
│   │   │       │   └── useMods.ts
│   │   │       ├── single-mod
│   │   │       │   ├── AmendingLawArticleEditorSingleMod.view.vue
│   │   │       │   ├── RisModForm.spec.ts
│   │   │       │   ├── RisModForm.story.vue
│   │   │       │   ├── RisModForm.vue
│   │   │       │   ├── useMod.spec.ts
│   │   │       │   └── useMod.ts
│   │   │       ├── useArticle.spec.ts
│   │   │       ├── useArticle.ts
│   │   │       ├── useModEidPathParameter.spec.ts
│   │   │       ├── useModEidPathParameter.ts
│   │   │       ├── useModEidSelection.spec.ts
│   │   │       └── useModEidSelection.ts
│   │   ├── overview
│   │   │   ├── AmendingLawOverview.view.vue
│   │   │   ├── RisAmendingLawCard.spec.ts
│   │   │   ├── RisAmendingLawCard.story.vue
│   │   │   └── RisAmendingLawCard.vue
│   │   ├── publishing
│   │   │   ├── Publishing.view.vue
│   │   │   ├── useAmendingLawRelease.spec.ts
│   │   │   └── useAmendingLawRelease.ts
│   │   └── temporal-data
│   │       ├── RisTemporalDataIntervals.spec.ts
│   │       ├── RisTemporalDataIntervals.story.vue
│   │       ├── RisTemporalDataIntervals.vue
│   │       └── TemporalData.view.vue
│   └── amending-laws
│       └── AmendingLaws.view.vue
├── vite-env.d.ts
└── vitest-setup.ts

26 directories, 200 files
```

</details>

And some reading that inspired this approach:

* https://dannorth.net/cupid-for-joyful-coding/#domain-based-structure
* https://povio.com/blog/maintainability-with-colocation/
* https://feature-sliced.design/
* https://github.com/alan2207/bulletproof-react/blob/master/docs/project-structure.md

[^1]: Or sometimes multiple routes when a nested router is used. But in those cases the sub-routes all are their own
features (as part of the bigger feature) as well.
