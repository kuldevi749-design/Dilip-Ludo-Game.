app/
 ├── java/com/dilip/ludo/
 │    ├── MainActivity.kt
 │    ├── GameActivity.kt
 │    ├── GameManager.kt
 │    ├── Player.kt
 │    ├── Token.kt
 │    └── Dice.kt
 │
 ├── res/layout/
 │    ├── activity_main.xml
 │    └── activity_game.xml
 │
 └── AndroidManifest.xml
package com.dilip.ludo

data class Player(
    val name: String,
    val color: String,
    var currentPosition: Int = 0
)
package com.dilip.ludo

class Dice {

    fun roll(): Int {
        return (1..6).random()
    }
}
package com.dilip.ludo

class GameManager {

    private val dice = Dice()

    val players = mutableListOf(
        Player("Player 1", "Red"),
        Player("Player 2", "Green"),
        Player("Player 3", "Yellow"),
        Player("Player 4", "Blue")
    )

    var currentPlayerIndex = 0

    fun rollDice(): Int {
        return dice.roll()
    }

    fun movePlayer(steps: Int) {
        val player = players[currentPlayerIndex]

        player.currentPosition += steps

        if (player.currentPosition > 56) {
            player.currentPosition = 56
        }

        if (steps != 6) {
            currentPlayerIndex =
                (currentPlayerIndex + 1) % players.size
        }
    }

    fun getCurrentPlayer(): Player {
        return players[currentPlayerIndex]
    }
}
package com.dilip.ludo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.startBtn)
            .setOnClickListener {

                startActivity(
                    Intent(this, GameActivity::class.java)
                )
            }
    }
}

package com.dilip.ludo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var manager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        manager = GameManager()

        val info = findViewById<TextView>(R.id.infoText)
        val diceText = findViewById<TextView>(R.id.diceText)
        val rollBtn = findViewById<Button>(R.id.rollBtn)

        updatePlayer(info)

        rollBtn.setOnClickListener {

            val dice = manager.rollDice()

            diceText.text = "Dice: $dice"

            manager.movePlayer(dice)

            updatePlayer(info)
        }
    }

    private fun updatePlayer(tv: TextView) {
        tv.text =
            "Turn: ${manager.getCurrentPlayer().name}"
    }
}
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:gravity="center"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:id="@+id/startBtn"
        android:text="Start Ludo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</LinearLayout>
 <?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:gravity="center"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/infoText"
        android:textSize="24sp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <TextView
        android:id="@+id/diceText"
        android:textSize="30sp"
        android:layout_marginTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

    <Button
        android:id="@+id/rollBtn"
        android:text="Roll Dice"
        android:layout_marginTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</LinearLayout>
