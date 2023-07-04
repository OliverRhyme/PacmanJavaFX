package dev.rhyme.pacmanjavafx

class Game(
    context: GameContext,
) {

    private val gameMap = GameMap(context = context)

    private val pacman = Pacman(
        position = gameMap.getPacmanInitialPosition(),
        context = context,
        gameMap = gameMap
    )

    private val ghosts = gameMap.getGhostInitialPositions().map {
        Ghost(
            position = it,
            context = context,
            gameMap = gameMap
        )
    }

    private val gameElements = buildList {
        add(gameMap)
        addAll(ghosts)
        add(pacman)
    }

    private val drawingContext = context.drawingContext

    fun gameLoop() {
        drawingContext.clearRect(0.0, 0.0, drawingContext.canvas.width, drawingContext.canvas.height)
        gameElements.forEach { it.update() }
    }

    fun resizeCanvas() {
        gameMap.resizeCanvas(drawingContext.canvas)
    }
}