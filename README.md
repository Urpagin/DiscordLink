# DiscordLink

DiscordLink provides an interface between the chat functionality of a Minecraft server and a Discord channel.

Here is a demo:

[Demo Video](https://github.com/Urpagin/DiscordLink/assets/72459611/ea6bf913-1dd4-4ba0-9f50-2040549207d3)


## 🏃‍♂️ Getting Started

DiscordLink is built on top of Spigot and is compatible with PaperMC onwards. **Currently, it is only available for Minecraft version 1.21**.

### Installation Steps

1. Download the `.jar` release file and place it in the `plugins` directory on your server.
2. Launch the server once to generate the config file at `plugins/DiscordLink/config.yml`.
3. Populate the `config.yml` with your Discord bot token and a channel ID.
4. Restart the server.
5. Enjoy!

> [!TIP]
> In the `plugins/DiscordLink/config.yml` file, set `minecraft_chat_prefixes` to `[]` in order for all Minecraft chat messages to be sent to the Discord channel.


## 🚨 Known Issues

- Executing the `reload` command on the Minecraft server leads to unexpected behavior (messages sent multiple times).


## 🛠️ Additional Information

The plugin interacts with Discord through the [JDA](https://github.com/discord-jda/JDA) library.


## 📝 Todo

- [x] "Cannot reply to a system message" (e.g.: pinned messages)
- [x] Use discord server nicknames in MC Chat, not handle
- [x] Custom description?
- [x] Custom Rich Presence
- [x] Customize prefixes in `config.yml` (e.g.: [";", ":", "."])
- [x] Death logging in the Discord channel
- [x] `/playing` slashcommand
- [x] `/deaths` slashcommand
- [ ] Sanitize formatting codes on Discord-sent messages
- [ ] `/stop` slashcommand & MC Command: stops the Minecraft server
- [ ] `/playtime` slashcommand: shows players playtime in a descending order
- [ ] Plugin version check at server start: checks this repo for newer releases
