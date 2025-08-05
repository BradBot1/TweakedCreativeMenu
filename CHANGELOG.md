# Additions
- Added new `insertion` object to custom tabs, usage is as follows:
    ```json
    "insertion": {
        "order": "before",
        "target": "minecraft:combat"
    }
    ```
    `order` is used to define where should the tab be inserted, valid options are `before` and `after`,
    and `target` is the target tab to anchor to.
- Added option to show tab IDs when Advanced Tooltips are enabled.

# Fixes
- Fixed proper row not being set on NeoForge.