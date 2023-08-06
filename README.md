# bucket-gcp
Serviço para fazer upload e download no bucket GCP
Criado para estudo do bucket GCP onde possui dois endpoints, um para upload e outro para donwload. 
O intuito no endpoint de upload é que client me envie um arquivo(JPEG, PDF, ...) e gere um UUID para salvar no banco transacional o caminho
da imagem.
O endpoint para download irá recuperar esse arquivo enviado pelo client.

