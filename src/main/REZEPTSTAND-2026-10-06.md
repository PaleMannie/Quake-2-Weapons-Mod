# Rezeptstand für spätere Versionsübertragungen — 2026-10-06

Auf Wunsch des Nutzers ist dieser Stand die Referenz für die spätere Übertragung der geänderten Rezepte auf andere Minecraft-/Loader-Versionen. Die Übertragung ist noch nicht durchgeführt.

Die folgenden 19 Rezepte stammen aus `resources/data/q2w/recipe`. Zutaten, Anordnung, Ausgabemengen und Schmelzparameter sollen erhalten bleiben. JSON-Format und Registry-IDs müssen bei der Übertragung an die jeweilige Zielversion angepasst werden. Die Rezepte gehören in den eigenen Mod-Namespace, nicht in `minecraft`.

Die Granatenkorrektur ist enthalten: Die erste Musterzeile lautet `" R "` mit einem Leerzeichen vor und nach R; sämtliche Zeilen sind drei Zeichen breit. Leerzeichen in Rezeptmustern sind relevant.

Projektvorgabe: Keine JAR erstellen.

## q2w_bfg10k.json

```json
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "pattern": [
    "DIS",
    "EAI",
    "DIL"
  ],
  "key": {
    "D": [
      "minecraft:diamond"
    ],
    "E": [
      "minecraft:ender_eye"
    ],
    "I": [
      "minecraft:iron_block"
    ],
    "L": "#minecraft:logs",
    "S": [
      "minecraft:stick"
    ],
    "A": [
      "minecraft:amethyst_shard"
    ]
  },
  "result": {
    "id": "q2w:q2w_bfg10k",
    "count": 1
  }
}
```

## q2w_blaster.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    " C ",
    "IRR",
    " CL"
  ],
  "key": {
    "I": ["minecraft:iron_ingot"],
    "L": "#minecraft:logs",
    "C": ["minecraft:copper_ingot"],
    "R": ["minecraft:redstone"]
  },
  "result": {
    "id": "q2w:q2w_blaster",
    "count": 1
  }
}
```

## q2w_bullet.json

```json
{
  "type": "minecraft:crafting_shapeless",
  "ingredients": [
    ["minecraft:iron_nugget"],
    ["minecraft:gold_nugget"],
    ["minecraft:gunpowder"]
  ],
  "result": {
    "id": "q2w:q2w_bullet",
    "count": 8
  }
}
```

## q2w_cell.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "IGI",
    "GRG",
    "IGI"
  ],
  "key": {
    "G": ["minecraft:glowstone_dust"],
    "I": ["minecraft:iron_ingot"],
    "R": ["minecraft:redstone"]
  },
  "result": {
    "id": "q2w:q2w_cell",
    "count": 16
  }
}
```

## q2w_chaingun.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "GRI",
    "RNW",
    "IWS"
  ],
  "key": {
    "N": ["q2w:q2w_machinegun"],
    "I": ["minecraft:iron_block"],
    "W": "#minecraft:planks",
    "S": ["minecraft:stick"],
    "R": ["minecraft:redstone"],
    "G": ["minecraft:gold_ingot"]
  },
  "result": {
    "id": "q2w:q2w_chaingun",
    "count": 1
  }
}
```

## q2w_grenade.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    " R ",
    "ITI",
    " G "
  ],
  "key": {
    "R": ["minecraft:redstone"],
    "G": ["minecraft:gunpowder"],
    "T": ["minecraft:tnt"],
    "I": ["minecraft:iron_ingot"]
  },
  "result": {
    "id": "q2w:q2w_grenade",
    "count": 8
  }
}
```

## q2w_grenadelauncher.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "rII",
    "DRB",
    "IIS"
  ],
  "key": {
    "I": ["minecraft:iron_block"],
    "B": ["minecraft:blaze_rod"],
    "S": ["minecraft:stick"],
    "R": ["minecraft:repeater"],
    "r": ["minecraft:redstone"],
    "D": ["minecraft:dispenser"]
  },
  "result": {
    "id": "q2w:q2w_grenadelauncher",
    "count": 1
  }
}
```

## q2w_hyperblaster.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "GI ",
    "BDR",
    " dL"
  ],
  "key": {
    "I": ["minecraft:iron_block"],
    "L": "#minecraft:logs",
    "B": ["q2w:q2w_blaster"],
    "D": ["minecraft:dispenser"],
    "d": ["minecraft:diamond"],
    "R": ["minecraft:repeater"],
    "G": ["minecraft:gold_block"]
  },
  "result": {
    "id": "q2w:q2w_hyperblaster",
    "count": 1
  }
}
```

## q2w_machinegun.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    " I ",
    "DIP",
    " CL"
  ],
  "key": {
    "I": ["minecraft:iron_block"],
    "P": ["minecraft:piston"],
    "C": ["minecraft:flower_pot"],
    "L": "#minecraft:logs",
    "D": ["minecraft:dispenser"]
  },
  "result": {
    "id": "q2w:q2w_machinegun",
    "count": 1
  }
}
```

## q2w_powershield_smelting.json

```json
{
  "type": "minecraft:smelting",
  "ingredient": [
    "q2w:q2w_powershield_item"
  ],
  "result": {
    "id": "minecraft:gold_ingot"
  },
  "experience": 0.7,
  "cookingtime": 200
}
```

## q2w_railgun.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "GRR",
    "dDQ",
    " IL"
  ],
  "key": {
    "D": ["minecraft:diamond"],
    "d": ["minecraft:dispenser"],
    "I": ["minecraft:iron_ingot"],
    "R": ["minecraft:redstone_block"],
    "L": "#minecraft:logs",
    "Q": ["minecraft:quartz"],
    "G": ["minecraft:ghast_tear"]
  },
  "result": {
    "id": "q2w:q2w_railgun",
    "count": 1
  }
}
```

## q2w_rebreather_smelting.json

```json
{
  "type": "minecraft:smelting",
  "ingredient": [
    "q2w:q2w_rebreather_item"
  ],
  "result": {
    "id": "minecraft:copper_ingot"
  },
  "experience": 0.7,
  "cookingtime": 200
}
```

## q2w_rocket.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "II ",
    "GTI",
    "II "
  ],
  "key": {
    "I": ["minecraft:iron_ingot"],
    "G": ["minecraft:gunpowder"],
    "T": ["minecraft:tnt"]
  },
  "result": {
    "id": "q2w:q2w_rocket",
    "count": 8
  }
}
```

## q2w_rocketlauncher.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "IIG",
    "DRB",
    "IIS"
  ],
  "key": {
    "I": ["minecraft:iron_block"],
    "S": ["minecraft:stick"],
    "R": ["minecraft:repeater"],
    "D": ["minecraft:dispenser"],
    "B": ["minecraft:blaze_rod"],
    "G": ["minecraft:gold_ingot"]
  },
  "result": {
    "id": "q2w:q2w_rocketlauncher",
    "count": 1
  }
}
```

## q2w_shell.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "IIP",
    "IIG",
    "IIB"
  ],
  "key": {
    "I": ["minecraft:iron_nugget"],
    "G": ["minecraft:gunpowder"],
    "P": ["minecraft:paper"],
    "B": ["minecraft:gold_ingot"]
  },
  "result": {
    "id": "q2w:q2w_shell",
    "count": 8
  }
}
```

## q2w_shotgun.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "  R",
    "IIP",
    "PWS"
  ],
  "key": {
    "I": ["minecraft:iron_block"],
    "R": ["minecraft:repeater"],
    "S": ["minecraft:stick"],
    "P": ["minecraft:piston"],
    "W": "#minecraft:planks"
  },
  "result": {
    "id": "q2w:q2w_shotgun",
    "count": 1
  }
}
```

## q2w_silencer_smelting.json

```json
{
  "type": "minecraft:smelting",
  "ingredient": [
    "q2w:q2w_silencer_item"
  ],
  "result": {
    "id": "minecraft:iron_ingot"
  },
  "experience": 0.7,
  "cookingtime": 200
}
```

## q2w_slug.json

```json
{
  "type": "minecraft:crafting_shapeless",
  "ingredients": [
    ["minecraft:iron_ingot"],
    ["minecraft:gold_ingot"],
    ["minecraft:copper_ingot"]
  ],
  "result": {
    "id": "q2w:q2w_slug",
    "count": 2
  }
}
```

## q2w_super_shotgun.json

```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    " GI",
    "ISW",
    "PWs"
  ],
  "key": {
    "I": ["minecraft:iron_block"],
    "G": ["minecraft:gold_ingot"],
    "S": ["q2w:q2w_shotgun"],
    "P": ["minecraft:piston"],
    "s": ["minecraft:stick"],
    "W": "#minecraft:planks"
  },
  "result": {
    "id": "q2w:q2w_super_shotgun",
    "count": 1
  }
}
```
