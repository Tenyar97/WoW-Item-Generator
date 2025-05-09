The program is fully runnable and designed to make creating SQL entries for items way faster, easier, and more reliable — especially on cores like CMaNGOS, Trinity, exc.

Here’s what it can do right now:

~Generate full SQL INSERT strings for item_template with just a few clicks.

~Fill in key fields like entry ID, item class, subclass, name, display ID, quality, vendor prices, stats, spells, requirements, and loot settings.

~Dropdowns and smart selectors for inventory types, spell triggers, and other option-heavy fields

~Randomize vendor pricing if you want some extra flavor.

~Handle optional fields like required skills, limited bag slots, and more.

I wanted to make is as simple as possible, so all dropdowns and selections are readable, understandable text so you no longer have to look up anything manually or remember values.

Export your SQL directly to a file, or copy it to clipboard instantly for testing.
The thing that really kicked off this project was all the other tools I tried to use either didn't work for my core, or had broken field values — so I made the SQL field names configurable.
If your server’s database expects displayID instead of display_id, or ItemLevel instead of itemlevel, you can easily adjust the field names in the config file. No code editing necessary.
