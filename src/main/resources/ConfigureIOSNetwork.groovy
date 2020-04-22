job ('Configure iOS Network') {
    scm {
        git('https://github.com/abrashamc/IosNetworkConfig.git') { -> node
            node / gitConfigName('DSL User')
            node / gitConfigEmail('abrasham.chowdhury@disneystreaming.com')
        }
    }
}