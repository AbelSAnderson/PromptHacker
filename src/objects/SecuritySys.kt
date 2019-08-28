package objects

class SecuritySys(var ports: Array<Port>, var password: String, var hint: String, var hintCount: Int = 3, var isLocked: Boolean = true)