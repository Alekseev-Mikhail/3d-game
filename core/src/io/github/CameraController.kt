package io.github

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector3

class CameraController(
    private val camera: Camera,
    var strafeLeftKey: Int,
    var strafeRightKey: Int,
    var forwardKey: Int,
    var backwardKey: Int,
    var velocity: Float,
    var sensitivity: Float,
) : InputAdapter() {
    private val tmp = Vector3()

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        val deltaX = -Gdx.input.deltaX * sensitivity
        val deltaY = -Gdx.input.deltaY * sensitivity
        camera.direction.rotate(camera.up, deltaX)
        tmp.set(camera.direction).crs(camera.up).nor()
        camera.direction.rotate(tmp, deltaY)
        return true
    }

    fun update() {
        update(Gdx.graphics.deltaTime)
    }

    fun update(deltaTime: Float) {
        if (Gdx.input.isKeyPressed(forwardKey)) {
            tmp.set(withoutY(camera)).nor().scl(deltaTime * velocity)
            camera.position.add(tmp)
        }
        if (Gdx.input.isKeyPressed(backwardKey)) {
            tmp.set(withoutY(camera)).nor().scl(-deltaTime * velocity)
            camera.position.add(tmp)
        }
        if (Gdx.input.isKeyPressed(strafeLeftKey)) {
            tmp.set(withoutY(camera)).crs(camera.up).nor().scl(-deltaTime * velocity)
            camera.position.add(tmp)
        }
        if (Gdx.input.isKeyPressed(strafeRightKey)) {
            tmp.set(withoutY(camera)).crs(camera.up).nor().scl(deltaTime * velocity)
            camera.position.add(tmp)
        }
        camera.update(true)
    }

    private fun withoutY(camera: Camera) = Vector3(camera.direction.x, 0f, camera.direction.z)
}
