package com.example.mynotks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotks.ui.theme.MyNotksTheme

@Composable
fun MainBackground() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ExitAlwaysBottomAppBar()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitAlwaysBottomAppBar() {
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Check, contentDescription = "check icon")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Edit, contentDescription = "edit icon")
                    }
                },
                /*TODO animate FAB - If a FAB is present,
                it detaches from the bar and remains on screen.*/
                floatingActionButton = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "add icon")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Text(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            text = "Example of scaffold with a bottom bar. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula dolor ligula, ac lacinia dui commodo nec. Aliquam erat volutpat. Phasellus vitae rhoncus lorem, vitae aliquam nunc. Donec rhoncus mattis arcu, ut aliquam nunc pellentesque ac. Maecenas sodales rhoncus suscipit. Integer sed nisi massa. Etiam a lectus massa. Quisque malesuada imperdiet ex id viverra. Quisque sit amet laoreet leo.\n" +
                    "\n" +
                    "Ut a magna ornare, consequat odio sed, tincidunt nulla. Etiam sit amet massa purus. Integer eu interdum diam, at aliquet risus. Donec lobortis id ipsum vel feugiat. Nulla aliquet malesuada venenatis. Phasellus in sapien scelerisque, ornare est eu, tristique nisi. Nunc." +
                    "Example of scaffold with a bottom bar. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula dolor ligula, ac lacinia dui commodo nec. Aliquam erat volutpat. Phasellus vitae rhoncus lorem, vitae aliquam nunc. Donec rhoncus mattis arcu, ut aliquam nunc pellentesque ac. Maecenas sodales rhoncus suscipit. Integer sed nisi massa. Etiam a lectus massa. Quisque malesuada imperdiet ex id viverra. Quisque sit amet laoreet leo.\n" +
                    "\n" +
                    "Ut a magna ornare, consequat odio sed, tincidunt nulla. Etiam sit amet massa purus. Integer eu interdum diam, at aliquet risus. Donec lobortis id ipsum vel feugiat. Nulla aliquet malesuada venenatis. Phasellus in sapien scelerisque, ornare est eu, tristique nisi. Nunc."+
                    "Example of scaffold with a bottom bar. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula dolor ligula, ac lacinia dui commodo nec. Aliquam erat volutpat. Phasellus vitae rhoncus lorem, vitae aliquam nunc. Donec rhoncus mattis arcu, ut aliquam nunc pellentesque ac. Maecenas sodales rhoncus suscipit. Integer sed nisi massa. Etiam a lectus massa. Quisque malesuada imperdiet ex id viverra. Quisque sit amet laoreet leo.\n" +
                    "\n" +
                    "Ut a magna ornare, consequat odio sed, tincidunt nulla. Etiam sit amet massa purus. Integer eu interdum diam, at aliquet risus. Donec lobortis id ipsum vel feugiat. Nulla aliquet malesuada venenatis. Phasellus in sapien scelerisque, ornare est eu, tristique nisi. Nunc."
        )

    }
    
}

@Preview
@Composable
fun MainBackgroundPreview() {
    MyNotksTheme {
        MainBackground()
    }
}