package com.example.lablearnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.example.lablearnandroid.ui.theme.LabLearnAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            context.startActivity(Intent(context, GalleryActivity::class.java))
                        }) {
                            Text("Go to Gallery Activity")
                        }
                        Button(
                            onClick = {
                                context.startActivity(Intent(context, SensorActivity::class.java))
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Go to Sensor Activity")
                        }
                        
                        Button(
                            onClick = {
                                context.startActivity(Intent(context, Part1Activity::class.java))
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Go to Part 1 Activity")
                        }
                        
                        Button(
                            onClick = {
                                context.startActivity(Intent(context, Part2Activity::class.java))
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Go to Part 2 Activity")
                        }
                        
                        Button(
                            onClick = {
                                val intent = Intent(context, DetailActivity::class.java).apply {
                                    putExtra("EXTRA_MESSAGE", "Testing Slide Up Transition!")
                                }
                                
                                // 2. สร้าง Custom Animation ตอนกดเปิด
                                val options = androidx.core.app.ActivityOptionsCompat.makeCustomAnimation(
                                    context, 
                                    R.anim.slide_in_up, // ให้ Detail สไลด์ขึ้น
                                    R.anim.stay // ตัว Main ให้อยู่นิ่งๆ
                                )
                                
                                context.startActivity(intent, options.toBundle())
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Go to Detail Activity (Slide Up)")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabLearnAndroidTheme {
        Text("Android")
    }
}