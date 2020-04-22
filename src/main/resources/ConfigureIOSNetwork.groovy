job ('Configure iOS Network Job') {
    scm {
        git('git://github.com/abrashamc/IosNetworkConfig.git') { -> node
            node / gitConfigName('abrashamc')
            node / gitConfigEmail('abrasham.chowdhury@disneystreaming.com')
        }
    }
}