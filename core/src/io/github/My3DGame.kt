package io.github

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT
import com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder

class My3DGame : ApplicationAdapter() {
    private lateinit var environment: Environment
    private lateinit var modelBatch: ModelBatch
    private lateinit var camera: PerspectiveCamera
    private lateinit var cameraController: CameraInputController
    private lateinit var model: Model
    private lateinit var instance: ModelInstance

    override fun create() {
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, GRAY))
        environment.add(DirectionalLight().set(WHITE, -1f, -0.8f, -0.2f))

        modelBatch = ModelBatch()

        camera = PerspectiveCamera(70f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(5f, 8f, 10f)
        camera.lookAt(6f, 0f, 0f)
        camera.near = 1f
        camera.far = 300f
        camera.update()

        cameraController = CameraInputController(camera)
        Gdx.input.inputProcessor = cameraController

        val modelBuilder = ModelBuilder()
        model = modelBuilder.createBox(
            5f,
            5f,
            5f,
            Material(ColorAttribute.createDiffuse(GRAY)),
            (Usage.Position or Usage.Normal).toLong(),
        )
        instance = ModelInstance(model)
    }

    override fun render() {
        cameraController.update()

        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        modelBatch.begin(camera)
        modelBatch.render(instance, environment)
        modelBatch.end()
    }

    override fun dispose() {
        modelBatch.dispose()
        model.dispose()
    }
}
