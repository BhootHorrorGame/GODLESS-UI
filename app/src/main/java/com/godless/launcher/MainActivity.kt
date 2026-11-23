package com.godless.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Black = Color(0xFF050706)
private val Surface = Color(0xFF101512)
private val Surface2 = Color(0xFF171E19)
private val Neon = Color(0xFF39FF14)
private val Muted = Color(0xFF9AA69D)
private val White = Color(0xFFF2F7F3)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GodlessApp() }
    }
}

private enum class Page(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Home("Home", Icons.Default.Home),
    Versions("Versions", Icons.Default.Layers),
    Mods("Mods", Icons.Default.Extension),
    Performance("Performance", Icons.Default.Speed),
    Settings("Settings", Icons.Default.Settings)
}

@Composable
private fun GodlessApp() {
    var page by remember { mutableStateOf(Page.Home) }
    var selectedProfile by remember { mutableStateOf("Survival - Fabric") }
    var showProfileDialog by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Neon,
            onPrimary = Black,
            background = Black,
            surface = Surface,
            onSurface = White,
            secondary = Neon
        )
    ) {
        Scaffold(
            containerColor = Black,
            bottomBar = {
                NavigationBar(
                    containerColor = Surface,
                    contentColor = White
                ) {
                    Page.entries.forEach { item ->
                        NavigationBarItem(
                            selected = page == item,
                            onClick = { page = item },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Neon,
                                selectedTextColor = Neon,
                                indicatorColor = Neon.copy(alpha = 0.12f),
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            when (page) {
                Page.Home -> HomeScreen(
                    padding = padding,
                    profile = selectedProfile,
                    onProfile = { showProfileDialog = true },
                    onNavigate = { page = it }
                )
                Page.Versions -> VersionsScreen(padding)
                Page.Mods -> ModsScreen(padding)
                Page.Performance -> PerformanceScreen(padding)
                Page.Settings -> SettingsScreen(padding)
            }
        }

        if (showProfileDialog) {
            AlertDialog(
                onDismissRequest = { showProfileDialog = false },
                containerColor = Surface2,
                title = { Text("Select profile", color = White) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Vanilla 1.21", "Survival - Fabric", "Forge Modpack", "Performance").forEach {
                            Text(
                                it,
                                color = if (it == selectedProfile) Neon else White,
                                modifier = Modifier.fillMaxWidth().clickable {
                                    selectedProfile = it
                                    showProfileDialog = false
                                }.padding(10.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showProfileDialog = false }) {
                        Text("CLOSE", color = Neon)
                    }
                }
            )
        }
    }
}

@Composable
private fun ScreenColumn(
    padding: PaddingValues,
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 18.dp, end = 18.dp, top = padding.calculateTopPadding() + 22.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, color = White, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                Text(subtitle, color = Muted, fontSize = 13.sp)
            }
        }
        item { Column(content = content) }
    }
}

@Composable
private fun HomeScreen(
    padding: PaddingValues,
    profile: String,
    onProfile: () -> Unit,
    onNavigate: (Page) -> Unit
) {
    ScreenColumn(padding, "GODLESS", "YOUR GAME. YOUR WORLD. YOUR RULES.") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(42.dp).clip(RoundedCornerShape(13.dp))
                    .background(Neon.copy(alpha = 0.13f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ViewInAr, contentDescription = null, tint = Neon)
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text("LAUNCHER", color = Neon, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("Minecraft Java Edition", color = Muted, fontSize = 12.sp)
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { onNavigate(Page.Settings) }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Muted)
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Surface),
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("READY TO PLAY?", color = Neon, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("Your world awaits.", color = White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    }
                    Box(
                        Modifier.size(46.dp).clip(CircleShape).background(Neon.copy(alpha = .12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.SportsEsports, null, tint = Neon, modifier = Modifier.size(25.dp))
                    }
                }
                HorizontalDivider(color = Muted.copy(alpha = .15f))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("GAME VERSION", color = Muted, fontSize = 10.sp)
                        Text("Minecraft 1.21.x", color = White, fontWeight = FontWeight.SemiBold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("LOADER", color = Muted, fontSize = 10.sp)
                        Text("Fabric", color = White, fontWeight = FontWeight.SemiBold)
                    }
                }
                OutlinedButton(
                    onClick = onProfile,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Neon),
                    border = BorderStroke(1.dp, Neon.copy(alpha = .6f))
                ) {
                    Icon(Icons.Default.Person, null, modifier = Modifier.size(17.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(profile, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.ExpandMore, null)
                }
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Neon, contentColor = Black),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, null)
                    Spacer(Modifier.width(8.dp))
                    Text("PLAY NOW", fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                }
                Text(
                    "UI PREVIEW — Minecraft engine integration is not connected yet.",
                    color = Muted, fontSize = 10.sp
                )
            }
        }

        Text("QUICK ACCESS", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickTile("Profiles", Icons.Default.Person, Modifier.weight(1f)) { onProfile() }
            QuickTile("Shaders", Icons.Default.BlurOn, Modifier.weight(1f)) { onNavigate(Page.Mods) }
            QuickTile("Controls", Icons.Default.Gamepad, Modifier.weight(1f)) { onNavigate(Page.Settings) }
        }
        Text("RECENT ACTIVITY", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        InfoCard(Icons.Default.History, "No recent sessions", "Your launched game sessions will appear here.")
    }
}

@Composable
private fun QuickTile(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Surface2),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(vertical = 17.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, null, tint = Neon)
            Text(label, color = White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun VersionsScreen(padding: PaddingValues) {
    val versions = listOf(
        "Minecraft 1.21.x" to "Release • Vanilla",
        "Minecraft 1.20.4" to "Release • Fabric compatible",
        "Minecraft 1.19.2" to "Release • Forge compatible",
        "Minecraft 1.16.5" to "Release • Legacy profile"
    )
    ScreenColumn(padding, "Versions", "Manage Minecraft installations") {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Neon, contentColor = Black)
        ) {
            Icon(Icons.Default.Add, null)
            Spacer(Modifier.width(8.dp))
            Text("ADD VERSION")
        }
        versions.forEach { (name, detail) ->
            InfoCard(Icons.Default.Layers, name, detail, trailing = "DEMO")
        }
        Text("Version installation will be connected to the Amethyst core in a later integration phase.",
            color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun ModsScreen(padding: PaddingValues) {
    var tab by remember { mutableStateOf("Mods") }
    var enabled by remember { mutableStateOf(true) }
    ScreenColumn(padding, "Library", "Mods, shaders and resource packs") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Mods", "Shaders", "Packs").forEach { label ->
                FilterChip(
                    selected = tab == label,
                    onClick = { tab = label },
                    label = { Text(label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Neon.copy(alpha = .15f),
                        selectedLabelColor = Neon
                    )
                )
            }
        }
        if (tab == "Mods") {
            Text("INSTALLED MODS", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            listOf("Sodium (demo)", "Lithium (demo)", "Mod Menu (demo)").forEach { mod ->
                Card(colors = CardDefaults.cardColors(containerColor = Surface2)) {
                    Row(
                        Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Extension, null, tint = Neon)
                        Spacer(Modifier.width(12.dp))
                        Text(mod, color = White, modifier = Modifier.weight(1f))
                        Switch(checked = enabled, onCheckedChange = { enabled = it })
                    }
                }
            }
        } else {
            InfoCard(
                if (tab == "Shaders") Icons.Default.BlurOn else Icons.Default.Collections,
                "No $tab installed",
                "Imported content will appear here after native file and loader integration."
            )
        }
        OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.FileOpen, null)
            Spacer(Modifier.width(8.dp))
            Text("IMPORT FROM DEVICE")
        }
        Text("Demo entries only. No mod or shader files are installed by this prototype.",
            color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun PerformanceScreen(padding: PaddingValues) {
    var memory by remember { mutableFloatStateOf(2048f) }
    var fps by remember { mutableStateOf("60") }
    var preset by remember { mutableStateOf("Balanced") }
    ScreenColumn(padding, "Performance", "Configure per-profile game preferences") {
        Card(colors = CardDefaults.cardColors(containerColor = Surface)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("MEMORY ALLOCATION", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text("${memory.toInt()} MB", color = Neon, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Slider(
                    value = memory,
                    onValueChange = { memory = it },
                    valueRange = 512f..4096f,
                    steps = 13,
                    colors = SliderDefaults.colors(thumbColor = Neon, activeTrackColor = Neon)
                )
                Text("Demo control. Actual safe limits must be calculated from device memory.",
                    color = Muted, fontSize = 10.sp)
            }
        }
        Text("FPS TARGET (PREFERENCE)", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("30", "60", "90", "120").forEach {
                FilterChip(
                    selected = fps == it,
                    onClick = { fps = it },
                    label = { Text(it) }
                )
            }
        }
        Text("GRAPHICS PRESET", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        listOf("Battery Saver", "Balanced", "Quality").forEach {
            Row(
                Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                    .background(Surface2).clickable { preset = it }.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = preset == it, onClick = { preset = it }, colors = RadioButtonDefaults.colors(selectedColor = Neon))
                Text(it, color = White)
            }
        }
        InfoCard(Icons.Default.MonitorHeart, "Live FPS", "Not connected — game telemetry integration required.")
    }
}

@Composable
private fun SettingsScreen(padding: PaddingValues) {
    var notifications by remember { mutableStateOf(false) }
    var animations by remember { mutableStateOf(true) }
    ScreenColumn(padding, "Settings", "Customize your GODLESS experience") {
        Text("APPEARANCE", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        InfoCard(Icons.Default.Palette, "Theme", "Pure Black + Neon Green")
        Text("APP PREFERENCES", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        SettingSwitch("UI animations", "Smooth interface transitions", animations) { animations = it }
        SettingSwitch("Notifications", "Launcher notifications", notifications) { notifications = it }
        Text("GAME CONFIGURATION", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        InfoCard(Icons.Default.Terminal, "Java runtime", "Will be provided by Amethyst integration")
        InfoCard(Icons.Default.Folder, "Game directory", "Not configured")
        InfoCard(Icons.Default.Gamepad, "Touch controls", "Default layout • Editor integration planned")
        Text("GODLESS Launcher • UI Prototype v0.1.0", color = Muted, fontSize = 10.sp)
    }
}

@Composable
private fun SettingSwitch(
    title: String,
    subtitle: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
            .background(Surface2).padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, color = White, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = Muted, fontSize = 11.sp)
        }
        Switch(checked = checked, onCheckedChange = onChecked, colors = SwitchDefaults.colors(checkedThumbColor = Black, checkedTrackColor = Neon))
    }
}

@Composable
private fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    trailing: String? = null
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Surface2),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = Neon)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text(subtitle, color = Muted, fontSize = 11.sp)
            }
            if (trailing != null) {
                Text(trailing, color = Neon, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
