package com.example.notepad3

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notepad3.ui.theme.Notepad3Theme
import java.util.Date
import java.util.Locale
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Notepad3Theme {
                NotepadApp()
            }
        }
    }
}

@Composable
fun NotepadApp() {
    var noteText by remember { mutableStateOf("") }
    var drawerOpen by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalDrawer(
        drawerContent = {
            // 这里添加抽屉菜单的内容，例如一些设置选项等
            Text("抽屉菜单内容")
        },
        drawerState = drawerState,
        modifier = Modifier,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = { TopTitleBar() },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // 在这里处理按钮点击事件，例如保存笔记
                        // 例如：onSaveNote(noteText)
                        // 然后清空输入框：noteText = ""
                        println("FloatingActionButton clicked!")
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "添加笔记",
                        tint = Color.White
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                TitleBar()
                TextInput(
                    value = noteText,
                    onValueChange = { noteText = it }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopTitleBar() {
    TopAppBar(
        title = {
            Text(
                "我的记事本",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
private fun TitleBar() {
    val currentData = SimpleDateFormat("MM月dd日", Locale.getDefault()).format(Date())
    val currentWeek = SimpleDateFormat("EEEE", Locale.CHINA).format(Date())
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currentData,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = currentWeek,
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.sleet),
            contentDescription = "Calendar Icon",
            modifier = Modifier
                .size(60.dp)
                .padding(end = 16.dp)
        )
    }
}

@Composable
private fun TextInput(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxSize(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        placeholder = { Text("在这里输入你的笔记...") }
    )
}


@Preview(showBackground = true)
@Composable
fun TitleBarPreview() {
    Notepad3Theme {
        TitleBar()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotepadApp() {
    Notepad3Theme {
        NotepadApp()
    }
}

