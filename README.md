请注意，此页面使用 AI 翻译，可能存在不准确或错误的地方。欢迎提交 PR 帮助我们改进翻译质量。

<p align="center">
    <img src="https://github.com/user-attachments/assets/886e7e3c-8231-42cb-bb9e-3bf687b4ade7" alt="STBR"/>
</p>

### Sensible Toolbox Reborn (STBR) 是原始 Sensible Toolbox 插件的延续和现代化版本。该插件最初由 [@desht](https://github.com/desht) 在 2014 年开发，此分支已得到复兴，以使其与现代 Minecraft 版本保持同步，并包括各种改进和修复。原始存储库可以在[此处](https://github.com/desht/sensibletoolbox)找到。
> [!TIP]
> 此分支在保持插件核心原则的同时引入了许多新功能和增强功能。无论你是在自动化农场，还是构建复杂系统，STBR 都将继续提供用户友好的体验！
>
> ### 支持的 MC 版本：1.20+
>
## :gear: 关键功能和更新
> [!IMPORTANT]
> 以下是 Sensible Toolbox Reborn 的第一个版本中引入的一些主要改进和修复：
>
> 添加的拉取请求：[#44](https://github.com/Slimefun/SensibleToolbox/pull/44)，[#60](https://github.com/Slimefun/SensibleToolbox/pull/60)，[#85](https://github.com/Slimefun/SensibleToolbox/pull/85)，[#89](https://github.com/Slimefun/SensibleToolbox/pull/89)，[#90](https://github.com/Slimefun/SensibleToolbox/pull/90)，[#91](https://github.com/Slimefun/SensibleToolbox/pull/91)，[#120](https://github.com/Slimefun/SensibleToolbox/pull/120)，[#121](https://github.com/Slimefun/SensibleToolbox/pull/121)
> ```
> 神圣方块：
> - 如果用户处于创造模式，则方块不再被移除
> 自动建造器：
> - 告示牌正确更新且外观更好
> - 当机器耗尽电力或材料，或遇到无法破坏的方块时，会添加停止状态；在 GUI 和告示牌中可视化
> - 修复了清除模式的方块硬度检查，并移除了液体
> - 添加了新粒子，以便更好地可视化机器运行时的位置
> - 修复了一些 UI 元素
> 自动农场：
> - 修复了半径检查
> - 修复了机器工作区域和充电问题
> - 自动农场现在可以正确收获材料并设置正确的作物年龄
> 自动农场 MK II：
> - 修复了半径检查
> - 现在可以收获甜浆果
> 自动伐木机：
> - 修复了半径检查
> - 正确移除并重植树苗
> BSU：
> - 修复了主要复制问题 [#85]
> 组合锄头：
> - 说明文字已修复，更加一致
> - 种子袋再次可用
> - 如果存在（异国情调的花园，耕作），组合锄头将清除树叶方块存储数据
> - 权限检查
> - 新组合锄头：下界合金；7x7 的类似合成过程
> 电梯：
> - 现在可以在 y0 以下工作并正确获取最低世界高度
> 地狱农场：
> - 修复了半径检查
> 多建造器：
> - 当使用能量单元时，充电速率从 100 提高到 500
> - 构建模式：
>   - 以前的构建模式使用“洪水填充”方法，该方法非常不稳定且有点令人困惑。构建模式现在将在你面对的面上构建一排方块。非常适合构建列或行，这与其他插件中的类似“建造魔杖”不同。
>   - 如果放置的方块会与玩家相交，则构建模式中的方块现在将停止放置
>   - 添加了消息，以在构建时指示构建了多少个方块以及方向
> - 交换模式：
>   - 现在交换使用类似于构建模式的线性机制
>   - 如果玩家没有所需的方块进行交换，则添加消息通知玩家
> 画笔/滚筒：
> - 用油漆罐右击填充画笔或滚筒时，将不再打开 GUI
> 红石时钟：
> - 打开 GUI 时，现在取消了右击事件
> 发送模块：
> - 修复了向诸如箱子和漏斗等方块实体发送物品的问题
> 速度升级（机器）：
> - 现在可以应用于所有自动农场机器
> 卷尺：
> - 输出文本现在显示从锚点到测量总块数的距离
> 其他：
> - 修复了众多漏洞
> - 移除了 > PlayerItemHeldEvent 的取消
> - 土地标记器不再适用于 STB 机器
> - 修复了 Vanilla 库存插入问题
> - 现在发光物品上不再显示附魔
> - 将一些物品从“方块”类别移动到“物品”类别
> - 总体改善了告示牌的更新
> - 现在发光效果适用于著名工具和 IR 模块
> - 物品说明文字和消息的颜色更加一致
> ```
## 📺 媒体

<details> 
  <summary>更快的自动农场！如果你的服务器有更高的刻率，或者你有更快的生长方法，现在你可以使用速度升级来跟上节奏！</summary>
  <img src="https://github.com/user-attachments/assets/1c3a1e78-8965-4e56-a4c0-b3f79ccf53c2">
</details>

<details> 
  <summary>使用新的下界合金组合锄头耕地、播种和收获！7x7 的半径意味着你只需点击几下就可以种植大片农场！</summary>
  <img src="https://github.com/user-attachments/assets/8c087917-2f5a-400d-9861-41e872c01e45">
</details>

<details> 
  <summary>使用全新设计的多建造器快速构建列和行！</summary>
  <img src="https://github.com/user-attachments/assets/8e156f37-589e-4e1f-8807-2c3e5a195a82">
</details>

<details> 
  <summary>多建造器的交换模式也已重新设计。轻松快速替换地板和天花板！</summary>
  <img src="https://github.com/user-attachments/assets/3724e906-d4e7-452a-a5b6-ab75afe78066">
</details>

<details> 
  <summary>别忘了用效率附魔来减少耐久度损失，用锋利附魔来覆盖更大区域，以增强多建造器的性能！</summary>
  <img src="https://github.com/user-attachments/assets/d8854e17-cdf7-48f1-b2b6-39a5bfd8bb07">
</details>

<details> 
  <summary>使用强大的自动建造器来构建大型（或小型）长方体，清除整个区块，或者只是为你的温馨小屋建一堵墙！</summary>
  <img src="https://github.com/user-attachments/assets/f0b41726-3400-41bb-b2d0-9b4002461b53">
</details>

## :floppy_disk: 下载 Sensible Toolbox Reborn
在此处下载最新版本：[发行版](https://github.com/Bunnky/SensibleToolbox/releases/)

> [!WARNING]
> STBR 目前正在积极开发中，构建可能仍然不稳定。代码库已有 10 多年的历史，因此请谨慎将其添加到实时服务器。

## :headphones: Discord 支持服务器
> [!CAUTION]
> 对于错误报告，请使用[问题跟踪器](https://github.com/SlimefunGuguProject/SensibleToolboxReborn/issues)，而不是 Discord。官方版本的 SensibleToolbox 几乎没有支持，并且没有更新以在 1.18 之后的版本中工作。
>
> ### **请联系或在 discord 中 @Bunnky 以获取实时支持！请使用 [#addons](https://discord.com/channels/565557184348422174/624966637816381440) 频道**

<p align="center">
  <a href="https://discord.gg/slimefun">
    <img src="https://discordapp.com/api/guilds/565557184348422174/widget.png?style=banner3" alt="Discord 邀请"/>
  </a>
</p>

## :bulb: 致谢
此分支基于原始的 [Slimefun/SensibleToolbox](https://github.com/Slimefun/SensibleToolbox).
